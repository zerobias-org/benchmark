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
├── package/zerobias/          # Community benchmark packages
│   └── <benchmark-name>/      # Individual benchmark
│       ├── package.json       # NPM package configuration
│       ├── index.yml          # Benchmark metadata
│       ├── elements.yml       # Test cases and remediation
│       ├── CHANGELOG.md       # Version history
│       └── npm-shrinkwrap.json
├── scripts/                   # Creation and validation scripts
├── lerna.json                 # Monorepo configuration
└── README.md
```

## File Format Reference

**Source of Truth:** `../../com/platform/dataloader/src/processors/standard/benchmark/`

**Expected Structure:**
- `index.yml` - Benchmark metadata
- `elements.yml` - Test cases with remediation steps
- `package.json` - Must include `auditmation.import-artifact: "standard"` and `standard_type: "benchmark"`

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
- **[com/platform/dataloader/CLAUDE.md](../../com/platform/dataloader/CLAUDE.md)** - Dataloader processor

---

**Last Updated:** 2025-11-11
**Maintainers:** ZeroBias Community

