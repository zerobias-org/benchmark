# Benchmark mono-repo

Open-source **benchmark** content artifacts under `@zerobias-org`. A benchmark is
prescriptive HOW-TO guidance (test cases + remediation) tied to a parent suite,
loaded into the ZeroBias platform by the dataloader. Build/publish runs on the
gradle (`zb.content`) + zbb pipeline — there is no lerna/nx.

> This repo is bootstrapped and ready but has **no community benchmarks yet**.
> The `examples/` fixture is reference-only (not published, not part of the build).

## Layout

```
package/<vendor>/<suite>/<version>/   # one npm package per benchmark (depth 3)
  package.json     # @zerobias-org/benchmark-<vendor>-<suite>-<version>
                   #   zerobias.package = <vendor>.<suite>.<version>.benchmark
  index.yml        # benchmark metadata (standardType: benchmark, elementTypes, mappingTypes)
  elements/        # required — test cases (each its own id UUID)
  baselines/       # optional — baselines (each its own id UUID)
  .npmrc
  build.gradle.kts # one-line marker: plugins { id("zb.content") }
  gate-stamp.json  # written by ./gradlew :<v>:<s>:<ver>:gate
bundle/            # @zerobias-org/benchmark-bundle (workflow-managed)
```

## Authentication

- `ZB_TOKEN` — npm registry (`@zerobias-org` → `pkg.zerobias.org`). Get an API key at [app.zerobias.com](https://app.zerobias.com).
- `NPM_TOKEN` — GitHub Packages (`@auditlogic` / `@zerobias-com`).
- `NEON_API_KEY` + `NEON_PROJECT_ID` — dataloader integration (sourced from vault in CI).

## Creating a new benchmark

```sh
scripts/createNewBenchmark.sh <standard_category> <vendor> <suite> <version>
```

- `standard_category`: cyber | technical | clinical
- `vendor`: publishing vendor, e.g. `nist`
- `suite`: the suite/category, e.g. `800-53`
- `version`: e.g. `v1` | `2024` | `rev4`

This scaffolds `package/<vendor>/<suite>/<version>/` from `templates/`, copies the
`.npmrc`, and drops the `build.gradle.kts` marker. Then:

1. Fill in `index.yml` (`{field}` placeholders), `elementTypes`, and `mappingTypes`
   (see `examples/` for structure).
2. Add `elements/` test cases. Mapping-type elements (test cases) also need
   `{elementCode}-acceptanceCriteria.md` and `{elementCode}-remediation.md`.
3. Optionally add `baselines/*.yml` (e.g. `low`/`medium`/`high`).
4. Point the package's `dependencies` at the real parent suite
   (`@zerobias-org/suite-<vendor>-<suite>`).

## Validate

```bash
./gradlew :<vendor>:<suite>:<version>:gate   # validator + dataloader + writes gate-stamp.json
./gradlew validateUniqueIds                  # cross-cut: no duplicate ids across index/elements/baselines
```

The gate runs the repo-owned validator (filesystem ↔ npm-name ↔ `zerobias.package`
triangulation, required files, unique ids) and `testIntegrationDataloader`, which
loads the benchmark against an ephemeral Neon branch. Without Neon creds the
dataloader step is skipped locally and re-runs in CI. **Commit `gate-stamp.json`**
alongside the package.

## Publishing

Driven by `zerobias-org/devops/.github/workflows/zbb-publish-reusable.yml` on push to
`main`/`qa`/`dev`/`uat` (see `.github/workflows/publish.yml`). It detects changed
packages, single-writer version-bumps on `main`, publishes, refreshes the bundle, and
syncs branches. Do not `npm publish` by hand. PRs target `dev`.

## Commit conventions

[Conventional Commits](https://www.conventionalcommits.org/), enforced by commitlint:
`<type>(<scope>): <subject>` — types: feat, fix, docs, style, refactor, perf, test, chore.
