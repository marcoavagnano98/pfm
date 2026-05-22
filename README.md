# PFM

Personal Finance Manager is a native Android app for offline, manual personal finance tracking.

## Current Scope

Implemented from `docs/en/05-delivery-plan.md`:
- Sprint 0: Android foundation, Compose, Material 3, Navigation, Hilt, Room, DataStore.
- Sprint 1: accounts create/edit/list/archive.
- Sprint 2: transactions create/edit/list for income, expense, and transfer.
- Sprint 3: budget profile, planned expenses, balances, available and remaining budget.
- Sprint 4: home dashboard and period summaries.
- Sprint 5: focused unit tests for core calculations and validations.

## Requirements

- Android Studio or Android command-line SDK.
- JDK 17.
- Android SDK platform `android-35`.
- The repository Gradle wrapper, or Android Studio using the configured Gradle project.

## Useful Commands

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
sh scripts/static-check.sh
sh scripts/verify-local.sh
```

`scripts/verify-local.sh` always runs static checks. It also runs unit tests and debug assembly when local Java and Gradle tooling are available.

Implementation history is tracked in `docs/en/implementation-update.md`.
Current readiness evidence and remaining verification steps are tracked in `docs/en/release-readiness-audit.md`.
Sprint deliverable source evidence is tracked in `docs/en/sprint-deliverables-matrix.md`.
Manual runtime checks are listed in `docs/en/manual-smoke-test.md`.

## CI

`.github/workflows/android.yml` runs static checks, unit tests, and debug assembly on pushes and pull requests using JDK 17 and the Gradle wrapper.
