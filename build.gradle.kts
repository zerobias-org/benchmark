import com.zerobias.buildtools.content.SchemaPrimitives

plugins {
    id("zb.workspace")
}

group = "com.zerobias.content"

// ════════════════════════════════════════════════════════════
// Benchmark content validator — owned by this repo.
//
// Philosophy (per Chris/Kevin): the dataloader is the source of truth
// for schema rules (UUID format, standardType/elementType/mappingType
// enums, parent-suite lookup, element/baseline structure, etc.).
// Re-validating those here just creates drift risk — when the dataloader
// tightens a rule, the gate gets stale. The full schema is exercised by
// testIntegrationDataloader against an ephemeral Neon branch during gate.
// See com/platform/dataloader/src/processors/standard/benchmark/
// (BenchmarkArtifactLoader, BenchmarkElementFileHandler,
// BenchmarkBaselineFileHandler; shared StandardIndexFileHandler).
//
// Benchmark payload shape (mirrors standard):
//   package/<vendor>/<suite>/<version>/
//     index.yml        — benchmark metadata (standardType: benchmark)
//     elements/...      — required; benchmark elements (each its own id)
//     baselines/...     — optional; baselines (each its own id)
//
// This validator only enforces things the dataloader CANNOT or DOES NOT
// check:
//
//   1. Filesystem ↔ npm ↔ zerobias-block triangulation:
//        dir              = package/<vendor>/<suite>/<version>/
//        npm name         = @zerobias-org/benchmark-<vendor>-<suite>-<version>
//        zerobias.package = <vendor>.<suite>.<version>.benchmark
//      npm name keeps directory segments verbatim (npm allows hyphens);
//      zerobias.package segments must be hyphen-free dataloader keys, so
//      vendor/suite hyphens are STRIPPED (mirroring the parent suite's
//      code, e.g. nist/800-53 → nist.80053) and the version segment's
//      dots/hyphens become underscores (e.g. v1.5 → v1_5).
//
//   2. Required files/dirs exist (index.yml, package.json, .npmrc, a
//      non-empty elements/ directory).
//
//   3. Repo-wide unique `id` UUIDs across index.yml AND every
//      elements/*.yml / baselines/*.yml (separate :validateUniqueIds
//      task). The dataloader processes one artifact at a time, so a
//      collision only surfaces when the second tries to overwrite the
//      first's DB row.
//
// Everything else delegated to the dataloader in testIntegrationDataloader.
// ════════════════════════════════════════════════════════════
extra["contentValidator"] = { proj: org.gradle.api.Project ->
    val projectDir = proj.projectDir
    val tag = "[benchmark-validator] ${proj.path}"

    require(projectDir.resolve("index.yml").isFile)    { "$tag index.yml missing in ${projectDir.path}" }
    require(projectDir.resolve("package.json").isFile) { "$tag package.json missing in ${projectDir.path}" }
    require(projectDir.resolve(".npmrc").isFile)       { "$tag .npmrc missing in ${projectDir.path}" }
    require(projectDir.resolve("elements").isDirectory) { "$tag elements/ directory missing in ${projectDir.path}" }

    // ── 1. Filesystem ↔ npm ↔ zerobias-block triangulation ──
    val version = projectDir.name
    val suite = projectDir.parentFile.name
    val vendor = projectDir.parentFile.parentFile.name
    val pkgVendor = vendor.replace("-", "")
    val pkgSuite = suite.replace("-", "")
    val pkgVersion = version.replace(".", "_").replace("-", "_")

    val pkgDoc = SchemaPrimitives.parseJson(projectDir.resolve("package.json"))
    SchemaPrimitives.requirePackageIdentity(
        pkgDoc,
        expectedNpmName = "@zerobias-org/benchmark-$vendor-$suite-$version",
        expectedZerobiasPackage = "$pkgVendor.$pkgSuite.$pkgVersion.benchmark",
        field = "$tag package.json",
    )
    require(SchemaPrimitives.getPath(pkgDoc, "zerobias.import-artifact") == "benchmark" ||
            SchemaPrimitives.getPath(pkgDoc, "auditmation.import-artifact") == "benchmark") {
        "$tag zerobias.import-artifact must be 'benchmark'"
    }

    proj.logger.lifecycle("$tag: vendor=$vendor suite=$suite version=$version")
}

// ════════════════════════════════════════════════════════════
// :validateUniqueIds — repo-wide cross-cut over all *.yml.
// index.yml + elements/*.yml + baselines/*.yml each carry id UUIDs that
// must be globally unique.
// ════════════════════════════════════════════════════════════
val validateUniqueIds by tasks.registering {
    group = "verification"
    description = "Fail if two benchmark / element / baseline YAMLs share the same id UUID"

    val packageDir = layout.projectDirectory.dir("package").asFile
    inputs.files(
        fileTree(packageDir) {
            include("**/*.yml")
            exclude("**/node_modules/**")
        }
    )

    doLast {
        val byId = mutableMapOf<String, MutableList<String>>()
        if (packageDir.exists()) {
            packageDir.walkTopDown()
                .onEnter { it.name != "node_modules" }
                .filter { it.isFile && it.name.endsWith(".yml") }
                .forEach { f ->
                    val doc = try {
                        SchemaPrimitives.parseYaml(f)
                    } catch (e: Exception) {
                        logger.warn("[validateUniqueIds] skipping unparseable ${f.relativeTo(rootDir)}: ${e.message}")
                        return@forEach
                    }
                    val id = (doc["id"] as? String)?.lowercase() ?: return@forEach
                    byId.getOrPut(id) { mutableListOf() }.add(f.relativeTo(rootDir).path)
                }
        }

        val collisions = byId.filterValues { it.size > 1 }
        if (collisions.isNotEmpty()) {
            val report = collisions.entries.joinToString("\n") { (id, paths) ->
                "  $id\n    " + paths.joinToString("\n    ")
            }
            throw GradleException("[validateUniqueIds] duplicate benchmark/element/baseline ids across the repo:\n$report")
        }
        logger.lifecycle("[validateUniqueIds] ${byId.size} unique ids across ${byId.values.sumOf { it.size }} yaml files")
    }
}

subprojects {
    tasks.matching { it.name == "validateContent" }.configureEach {
        dependsOn(rootProject.tasks.named("validateUniqueIds"))
    }
}

val projectPaths by tasks.registering {
    group = "info"
    description = "Output project-to-directory mappings for tooling (used by zbb CLI)"
    doLast {
        subprojects.filter { it.buildFile.exists() }.forEach { p ->
            println("${p.path}=${p.projectDir.relativeTo(rootDir)}")
        }
    }
}

val changedModules by tasks.registering {
    group = "info"
    description = "List benchmark packages changed since last version tag"
    doLast {
        val lastTag = try {
            providers.exec { commandLine("git", "describe", "--tags", "--abbrev=0") }
                .standardOutput.asText.get().trim()
        } catch (e: Exception) {
            logger.warn("No version tags found -- listing all benchmark packages as changed")
            null
        }

        val diffArgs = if (lastTag != null) listOf("git", "diff", "--name-only", lastTag, "HEAD")
                       else listOf("git", "ls-files")

        val result = providers.exec { commandLine(diffArgs) }.standardOutput.asText.get()

        val packageDir = rootDir.resolve("package")
        val changed = mutableSetOf<String>()
        result.lines()
            .filter { it.startsWith("package/") }
            .forEach { line ->
                var dir = rootDir.resolve(line).parentFile
                while (dir != null && dir != packageDir && dir.startsWith(packageDir)) {
                    if (dir.resolve("build.gradle.kts").isFile) {
                        changed.add(dir.relativeTo(packageDir).path)
                        break
                    }
                    dir = dir.parentFile
                }
            }
        changed.forEach { println(it) }
    }
}
