package com.marco.pfm.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.marco.pfm.data.local.entity.BudgetProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget_profile WHERE id = 1 LIMIT 1")
    fun observeBudgetProfile(): Flow<BudgetProfileEntity?>

    @Upsert
    suspend fun upsertBudgetProfile(profile: BudgetProfileEntity)
}
