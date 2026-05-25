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

