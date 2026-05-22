package com.marco.pfm.data.repository

import com.marco.pfm.data.local.dao.CategoryDao
import com.marco.pfm.data.local.entity.CategoryEntity
import com.marco.pfm.data.local.mapper.toDomain
import com.marco.pfm.domain.model.Category
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.CategoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun getCategories(type: TransactionType?): Flow<List<Category>> =
        categoryDao.observeCategories(type?.name).map { categories ->
            categories.map(CategoryEntity::toDomain)
        }
}
