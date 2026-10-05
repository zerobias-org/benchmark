# CLAUDE.md - Community Benchmark Repository

This file provides guidance to Claude Code (claude.ai/code) when working with benchmark content in this repository.

## Project Overview

This is the **ZeroBias Community Benchmark Repository** containing open-source security benchmarks (test cases with remediation guidance). Benchmarks provide prescriptive HOW-TO guidance for achieving compliance, unlike frameworks which define WHAT must be done.

**Repository Role:** Community-contributed security benchmarks (CIS, STIG patterns)

This repository contains community-contributed, open-source benchmarks; a proprietary counterpart repository follows the same structure.

## Current Status

⚠️ **No authoring skill yet**

The repo is on the gradle + zbb pipeline and has one published benchmark (`owasp/wstg/v5`), but
there is no `create-benchmark` skill: authoring follows the structure, rules and commands in this
file. The only skill here is `/migrate-packages` (`.claude/skills/migrate-packages/SKILL.md`).

**Still needed:**
- Step-by-step workflows for creating new benchmarks
- Test case development and validation
- Remediation guidance authoring
- Automated test implementation
- Publishing and versioning guidelines

## Repository Structure

```
benchmark/
├── package/<vendor>/<suite>/<version>/   # Community benchmark packages (depth 3)
│   ├── package.json          # @zerobias-org/benchmark-<vendor>-<suite>-<version>
│   │                          #   zerobias.package = <vendor>.<suite>.<version>.benchmark
│   │                          #   zerobias.import-artifact = benchmark
│   ├── index.yml             # Benchmark metadata (standardType: benchmark)
│   ├── elements/             # Required; benchmark elements (each its own id)
│   ├── baselines/            # Optional; baselines (each its own id)
│   ├── .npmrc                # byte-identical copy of the repo-root .npmrc (cp it, never hand-write)
│   ├── npm-shrinkwrap.json   # shipped lockfile (in `files`), no `resolved` URLs
│   ├── build.gradle.kts      # one-line marker: plugins { id("zb.content") }
│   └── gate-stamp.json       # written by ./gradlew :<v>:<s>:<ver>:gate
├── bundle/                    # @zerobias-org/benchmark-bundle (workflow-managed)
├── templates/                 # scaffold for scripts/createNewBenchmark.sh
├── examples/                  # reference fixture (NOT published, not in the build)
├── scripts/createNewBenchmark.sh
├── build.gradle.kts           # root: benchmark validator + validateUniqueIds
├── settings.gradle.kts        # auto-discovers package/**/build.gradle.kts
└── zbb.yaml

> One benchmark is published so far: `package/owasp/wstg/v5`.
> Create another with `scripts/createNewBenchmark.sh <category> <vendor> <suite> <version>`
> (it drops the gradle marker), then `./gradlew :<vendor>:<suite>:<version>:gate`.
```

## File Format Reference

**Source of Truth:** the platform dataloader's benchmark processor. It is not part of this
open-source org; the gate runs the published loader against your package, so a gate pass is the
check that the files below are shaped correctly.

**Expected Structure:**
- `index.yml` - Benchmark metadata. `standardType: benchmark`, non-empty `elementTypes`, `mappingTypes`.
- `elements/*.yml` - required; test cases with remediation (each carries a unique `id` UUID).
- `baselines/*.yml` - optional; auto-generated default baseline if absent.
- `package.json` - `zerobias.import-artifact: "benchmark"`, `zerobias.package: "<vendor>.<suite>.<version>.benchmark"`.

## Element content rules

Elements (`elements/<code>.yml`) follow the element content rules. The full write-up is kept
with ZeroBias' internal docs and is not published in this org; the rules that apply here are:

- **`description`** — plain text, one line, **under 200 characters**: a summary, not the requirement text.
- **Full text → `elements/<code>-background.md`**, as markdown that renders (blank lines between
  paragraphs, `- a.` bullets, 4-space nesting, escaped digit labels).
- **`links`** aliases must resolve in the **live catalog** — the linker drops misses silently.
- Retire an element with `deprecate: true`, never by deleting the file.

**Enforced here:** `zb.elementRules=enforce` in `gradle.properties` makes `validateContent` fail
on any description/background violation (link-shape problems only warn) — effective
from the build-tools release carrying zerobias-org/util#120; older versions ignore the property.
There is no exceptions list: a violating package is fixed, not recorded.

Gate: this repo has no CI gate — run `zbb :<pkg>:gate` locally (dataloader on an ephemeral
Neon branch) and commit the refreshed `gate-stamp.json`. Versions: bump **minor** by hand in
the fix commit (content change).

To fix a package, apply the rules above by hand and re-gate. (ZeroBias maintainers have a
`fix-element-content` skill with survey/apply/verify scripts; it is not part of this org.)

## Build & validate (gradle + zbb)

```bash
./gradlew :<vendor>:<suite>:<version>:gate   # validate + dataloader + write gate-stamp
./gradlew validateUniqueIds                  # cross-cut: unique ids across all *.yml
./gradlew projectPaths                        # list discovered packages
```

**npm.** Every scope resolves from `pkg.zerobias.org` with `ZB_TOKEN` (see the root `.npmrc`).
Dependency specs are `"*"` — never `"latest"` or a `^` range; the shipped `npm-shrinkwrap.json`
pins the version. Generate it inside the package, after `package.json` is final, and `git add`
it before the gate (it is part of the gate-stamp hash):

```bash
npm install --package-lock-only --no-workspaces && mv package-lock.json npm-shrinkwrap.json
grep -c '"resolved"' npm-shrinkwrap.json   # must print 0
```

Publishing is driven by `zerobias-org/devops/.github/workflows/zbb-publish-reusable.yml`
on push to `main`/`qa`/`dev`/`uat` (see `.github/workflows/publish.yml`). No lerna/nx —
the gradle pipeline is the build/publish system. Don't reintroduce lerna or nx config.

## Benchmark vs Framework

**Framework (WHAT):**
- "Implement access controls"
- Non-prescriptive requirement
- Multiple valid implementations

**Benchmark (HOW):**
- "Configure SSH to use key-based authentication"
- Prescriptive test case with exact steps
- Specific implementation guidance
- Pass/fail criteria
- Remediation procedures

## Related Documentation

- **[Meta-repo CLAUDE.md](https://github.com/zerobias-org/zerobias/blob/main/CLAUDE.md)** - How the zerobias-org repos fit together
- **[ContentArtifacts.md](https://github.com/zerobias-org/zerobias/blob/main/docs/ContentArtifacts.md)** - Content catalog system
- **[Concepts.md](https://github.com/zerobias-org/zerobias/blob/main/docs/Concepts.md)** - Standard / framework / benchmark / crosswalk vocabulary
- **[zerobias-org/standard](https://github.com/zerobias-org/standard)** - Standard structure (benchmarks are a standard specialization)
- **[zerobias-org/framework](https://github.com/zerobias-org/framework)** - Sibling repo on the same pipeline

---

**Last Updated:** 2026-10-05
**Maintainers:** ZeroBias Community

