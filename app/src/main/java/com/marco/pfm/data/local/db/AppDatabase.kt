package com.marco.pfm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marco.pfm.data.local.dao.AccountDao
import com.marco.pfm.data.local.dao.BudgetDao
import com.marco.pfm.data.local.dao.CategoryDao
import com.marco.pfm.data.local.dao.PlannedExpenseDao
import com.marco.pfm.data.local.dao.TransactionDao
import com.marco.pfm.data.local.entity.AccountEntity
import com.marco.pfm.data.local.entity.BudgetProfileEntity
import com.marco.pfm.data.local.entity.CategoryEntity
import com.marco.pfm.data.local.entity.PlannedExpenseEntity
import com.marco.pfm.data.local.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        BudgetProfileEntity::class,
        PlannedExpenseEntity::class,
    ],
    version = 3,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun budgetDao(): BudgetDao
    abstract fun categoryDao(): CategoryDao
    abstract fun plannedExpenseDao(): PlannedExpenseDao
    abstract fun transactionDao(): TransactionDao
}
