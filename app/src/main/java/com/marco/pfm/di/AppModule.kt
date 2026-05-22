package com.marco.pfm.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.marco.pfm.data.datastore.UserPreferences
import com.marco.pfm.data.datastore.userPreferencesDataStore
import com.marco.pfm.data.local.dao.AccountDao
import com.marco.pfm.data.local.dao.BudgetDao
import com.marco.pfm.data.local.dao.CategoryDao
import com.marco.pfm.data.local.dao.PlannedExpenseDao
import com.marco.pfm.data.local.dao.TransactionDao
import com.marco.pfm.data.local.db.AppDatabase
import com.marco.pfm.data.repository.DefaultAccountRepository
import com.marco.pfm.data.repository.DefaultBudgetRepository
import com.marco.pfm.data.repository.DefaultCategoryRepository
import com.marco.pfm.data.repository.DefaultPlannedExpenseRepository
import com.marco.pfm.data.repository.DefaultTransactionRepository
import com.marco.pfm.domain.repository.AccountRepository
import com.marco.pfm.domain.repository.BudgetRepository
import com.marco.pfm.domain.repository.CategoryRepository
import com.marco.pfm.domain.repository.PlannedExpenseRepository
import com.marco.pfm.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val migration1To2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS categories (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    type TEXT NOT NULL
                )
                """.trimIndent(),
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_categories_type ON categories(type)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_categories_name ON categories(name)")
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    type TEXT NOT NULL,
                    amountMinor INTEGER NOT NULL,
                    dateEpochDay INTEGER NOT NULL,
                    sourceAccountId INTEGER,
                    destinationAccountId INTEGER,
                    categoryId INTEGER,
                    note TEXT,
                    createdAtEpochMillis INTEGER NOT NULL,
                    updatedAtEpochMillis INTEGER NOT NULL,
                    FOREIGN KEY(sourceAccountId) REFERENCES accounts(id) ON DELETE SET NULL,
                    FOREIGN KEY(destinationAccountId) REFERENCES accounts(id) ON DELETE SET NULL,
                    FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE SET NULL
                )
                """.trimIndent(),
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_dateEpochDay ON transactions(dateEpochDay)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_type ON transactions(type)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_sourceAccountId ON transactions(sourceAccountId)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_destinationAccountId ON transactions(destinationAccountId)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_transactions_categoryId ON transactions(categoryId)")
            seedCategories(db)
        }
    }

    private val migration2To3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS budget_profile (
                    id INTEGER PRIMARY KEY NOT NULL,
                    monthlyIncomeMinor INTEGER NOT NULL,
                    targetBudgetMinor INTEGER NOT NULL,
                    updatedAtEpochMillis INTEGER NOT NULL
                )
                """.trimIndent(),
            )
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS planned_expenses (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    amountMinor INTEGER NOT NULL,
                    isArchived INTEGER NOT NULL,
                    createdAtEpochMillis INTEGER NOT NULL,
                    updatedAtEpochMillis INTEGER NOT NULL
                )
                """.trimIndent(),
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_planned_expenses_isArchived ON planned_expenses(isArchived)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_planned_expenses_name ON planned_expenses(name)")
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "pfm.db",
    )
        .addMigrations(migration1To2, migration2To3)
        .addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    seedCategories(db)
                }
            },
        )
        .build()

    @Provides
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao =
        appDatabase.accountDao()

    @Provides
    fun provideBudgetDao(appDatabase: AppDatabase): BudgetDao =
        appDatabase.budgetDao()

    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao =
        appDatabase.categoryDao()

    @Provides
    fun providePlannedExpenseDao(appDatabase: AppDatabase): PlannedExpenseDao =
        appDatabase.plannedExpenseDao()

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao =
        appDatabase.transactionDao()

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountDao: AccountDao,
    ): AccountRepository = DefaultAccountRepository(accountDao)

    @Provides
    @Singleton
    fun provideBudgetRepository(
        budgetDao: BudgetDao,
    ): BudgetRepository = DefaultBudgetRepository(budgetDao)

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao,
    ): CategoryRepository = DefaultCategoryRepository(categoryDao)

    @Provides
    @Singleton
    fun providePlannedExpenseRepository(
        plannedExpenseDao: PlannedExpenseDao,
    ): PlannedExpenseRepository = DefaultPlannedExpenseRepository(plannedExpenseDao)

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
    ): TransactionRepository = DefaultTransactionRepository(transactionDao)

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context,
    ): UserPreferences = UserPreferences(context.userPreferencesDataStore)

    private fun seedCategories(db: SupportSQLiteDatabase) {
        db.execSQL("INSERT INTO categories (name, type) VALUES ('Salary', 'Income')")
        db.execSQL("INSERT INTO categories (name, type) VALUES ('Other income', 'Income')")
        db.execSQL("INSERT INTO categories (name, type) VALUES ('Food', 'Expense')")
        db.execSQL("INSERT INTO categories (name, type) VALUES ('Home', 'Expense')")
        db.execSQL("INSERT INTO categories (name, type) VALUES ('Transport', 'Expense')")
        db.execSQL("INSERT INTO categories (name, type) VALUES ('Other expense', 'Expense')")
    }
}
