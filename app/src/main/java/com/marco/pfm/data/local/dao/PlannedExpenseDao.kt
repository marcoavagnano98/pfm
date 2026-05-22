package com.marco.pfm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marco.pfm.data.local.entity.PlannedExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannedExpenseDao {
    @Query(
        """
        SELECT * FROM planned_expenses
        WHERE (:includeArchived = 1 OR isArchived = 0)
        ORDER BY isArchived ASC, name COLLATE NOCASE ASC
        """,
    )
    fun observePlannedExpenses(includeArchived: Boolean): Flow<List<PlannedExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlannedExpense(plannedExpense: PlannedExpenseEntity): Long

    @Update
    suspend fun updatePlannedExpense(plannedExpense: PlannedExpenseEntity)
}
