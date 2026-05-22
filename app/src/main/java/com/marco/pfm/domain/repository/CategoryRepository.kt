package com.marco.pfm.domain.repository

import com.marco.pfm.domain.model.Category
import com.marco.pfm.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(type: TransactionType? = null): Flow<List<Category>>
}
