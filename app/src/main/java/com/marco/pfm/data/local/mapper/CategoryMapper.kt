package com.marco.pfm.data.local.mapper

import com.marco.pfm.data.local.entity.CategoryEntity
import com.marco.pfm.domain.model.Category
import com.marco.pfm.domain.model.TransactionType

fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    name = name,
    type = runCatching { TransactionType.valueOf(type) }.getOrDefault(TransactionType.Expense),
)
