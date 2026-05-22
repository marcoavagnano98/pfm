package com.marco.pfm.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.marco.pfm.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query(
        """
        SELECT * FROM categories
        WHERE (:type IS NULL OR type = :type)
        ORDER BY name COLLATE NOCASE ASC
        """,
    )
    fun observeCategories(type: String?): Flow<List<CategoryEntity>>
}
