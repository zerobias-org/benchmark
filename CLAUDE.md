# CLAUDE.md - Community Benchmark Repository

This file provides guidance to Claude Code (claude.ai/code) when working with benchmark content in this repository.

## Project Overview

This is the **ZeroBias Community Benchmark Repository** containing open-source security benchmarks (test cases with remediation guidance). Benchmarks provide prescriptive HOW-TO guidance for achieving compliance, unlike frameworks which define WHAT must be done.

**Repository Role:** Community-contributed security benchmarks (CIS, STIG patterns)

This repository follows the same structure as `auditlogic/benchmark` but contains community-contributed, open-source benchmarks.

## Current Status

⚠️ **AI-Assisted Development Workflows Needed**

This CLAUDE.md is a placeholder. Comprehensive AI-assisted development workflows for creating and maintaining benchmarks are planned but not yet implemented.

**What's Needed:**
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
│   ├── .npmrc
│   ├── build.gradle.kts      # one-line marker: plugins { id("zb.content") }
│   └── gate-stamp.json       # written by ./gradlew :<v>:<s>:<ver>:gate
├── bundle/                    # @zerobias-org/benchmark-bundle (workflow-managed)
├── templates/                 # scaffold for scripts/createNewBenchmark.sh
├── examples/                  # reference fixture (NOT published, not in the build)
├── scripts/createNewBenchmark.sh
├── build.gradle.kts           # root: benchmark validator + validateUniqueIds
├── settings.gradle.kts        # auto-discovers package/**/build.gradle.kts
└── zbb.yaml

> No real community benchmarks exist yet — the repo is bootstrapped and ready.
> Create the first with `scripts/createNewBenchmark.sh <category> <vendor> <suite> <version>`
> (it drops the gradle marker), then `./gradlew :<vendor>:<suite>:<version>:gate`.
```

## File Format Reference

**Source of Truth:** `../../com/platform/dataloader/src/processors/standard/benchmark/`
(BenchmarkArtifactLoader, BenchmarkElementFileHandler, BenchmarkBaselineFileHandler;
shared StandardIndexFileHandler).

**Expected Structure:**
- `index.yml` - Benchmark metadata. `standardType: benchmark`, non-empty `elementTypes`, `mappingTypes`.
- `elements/*.yml` - required; test cases with remediation (each carries a unique `id` UUID).
- `baselines/*.yml` - optional; auto-generated default baseline if absent.
- `package.json` - `zerobias.import-artifact: "benchmark"`, `zerobias.package: "<vendor>.<suite>.<version>.benchmark"`.

## Element content rules

Elements (`elements/<code>.yml`) follow the element content rules — canonical reference:
[docs/ElementContentRules.md](../../docs/ElementContentRules.md) (meta-repo). In short:

- **`description`** — plain text, one line, **under 200 characters**: a summary, not the requirement text.
- **Full text → `elements/<code>-background.md`**, as markdown that renders (blank lines between
  paragraphs, `- a.` bullets, 4-space nesting, escaped digit labels).
- **`links`** aliases must resolve in the **live catalog** — the linker drops misses silently.
- Retire an element with `deprecate: true`, never by deleting the file.

**Enforced here:** `zb.elementRules=enforce` in `gradle.properties` makes `validateContent` fail
on any description/background violation (link-shape problems only warn) — effective
from the build-tools release carrying zerobias-org/util#120; older versions ignore the property. There is no
`element-rules-baseline.txt` and there should not be one — fix the package instead.

Gate: this repo has no CI gate — run `zbb :<pkg>:gate` locally (dataloader on an ephemeral
Neon branch) and commit the refreshed `gate-stamp.json`. Versions: bump **minor** by hand in
the fix commit (content change).

To fix a package, use the `fix-element-content` skill (meta-repo `.claude/skills/`), which
also carries the scripts for surveying, applying and verifying.

## Build & validate (gradle + zbb)

```bash
./gradlew :<vendor>:<suite>:<version>:gate   # validate + dataloader + write gate-stamp
./gradlew validateUniqueIds                  # cross-cut: unique ids across all *.yml
./gradlew projectPaths                        # list discovered packages
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

- **[Root CLAUDE.md](../../CLAUDE.md)** - Meta-repo guidance
- **[ContentArtifacts.md](../../ContentArtifacts.md)** - Content catalog system
- **[auditlogic/benchmark/CLAUDE.md](../../auditlogic/benchmark/CLAUDE.md)** - Proprietary benchmarks (same pattern)
- **[auditlogic/standard/CLAUDE.md](../../auditlogic/standard/CLAUDE.md)** - Standard structure
- **[auditmation/platform/dataloader/CLAUDE.md](../../auditmation/platform/dataloader/CLAUDE.md)** - Dataloader processor

---

**Last Updated:** 2025-11-11
**Maintainers:** ZeroBias Community

