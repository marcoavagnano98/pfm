#!/usr/bin/env sh
set -eu

check_no_matches() {
    pattern="$1"
    paths="$2"
    if rg -n "$pattern" $paths >/tmp/pfm-static-check.out; then
        cat /tmp/pfm-static-check.out
        exit 1
    fi
}

check_no_matches "TODO|FIXME|mipmap|exportSchema = true" "app README.md"

if rg -n "Double|Float" app >/tmp/pfm-static-check.out; then
    if rg -v "ExtendedFloatingActionButton" /tmp/pfm-static-check.out; then
        exit 1
    fi
fi

if rg -n "GetTransactionsUseCase|transactionRepository\\.getTransactions\\(" \
    app/src/main/java/com/marco/pfm/ui/features/accounts \
    app/src/main/java/com/marco/pfm/domain/usecase/GetHomeSummaryUseCase.kt \
    app/src/main/java/com/marco/pfm/domain/usecase/GetRemainingBudgetUseCase.kt \
    >/tmp/pfm-static-check.out; then
    cat /tmp/pfm-static-check.out
    exit 1
fi

if rg -n "domain\\.repository" app/src/main/java/com/marco/pfm/ui >/tmp/pfm-static-check.out; then
    cat /tmp/pfm-static-check.out
    exit 1
fi

if rg -n "getTransactionsSnapshot|suspend fun getTransactions\\(\\): List<TransactionEntity>" app >/tmp/pfm-static-check.out; then
    cat /tmp/pfm-static-check.out
    exit 1
fi

if rg -n "observeTransactions\\(|fun getTransactions\\(\\): Flow<List<Transaction>>" app/src/main/java >/tmp/pfm-static-check.out; then
    cat /tmp/pfm-static-check.out
    exit 1
fi

required_files="
app/src/main/java/com/marco/pfm/MainActivity.kt
app/src/main/java/com/marco/pfm/PfmApplication.kt
app/src/main/java/com/marco/pfm/data/local/db/AppDatabase.kt
app/src/main/java/com/marco/pfm/ui/PfmApp.kt
docs/en/implementation-update.md
docs/en/release-readiness-audit.md
"

for file in $required_files; do
    if [ ! -f "$file" ]; then
        echo "Missing required file: $file"
        exit 1
    fi
done

echo "Static checks passed."
