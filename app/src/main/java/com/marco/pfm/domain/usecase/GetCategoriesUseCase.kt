package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.Category
import com.marco.pfm.domain.model.TransactionType
import com.marco.pfm.domain.repository.CategoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    operator fun invoke(type: TransactionType? = null): Flow<List<Category>> =
        categoryRepository.getCategories(type)
}
