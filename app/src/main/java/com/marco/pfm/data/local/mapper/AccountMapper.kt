package com.marco.pfm.data.local.mapper

import com.marco.pfm.data.local.entity.AccountEntity
import com.marco.pfm.domain.model.Account
import com.marco.pfm.domain.model.AccountType
import java.time.Instant

fun AccountEntity.toDomain(): Account = Account(
    id = id,
    name = name,
    type = runCatching { AccountType.valueOf(type) }.getOrDefault(AccountType.Other),
    initialBalanceMinor = initialBalanceMinor,
    isArchived = isArchived,
    createdAt = Instant.ofEpochMilli(createdAtEpochMillis),
    updatedAt = Instant.ofEpochMilli(updatedAtEpochMillis),
)

fun Account.toEntity(): AccountEntity = AccountEntity(
    id = id,
    name = name,
    type = type.name,
    initialBalanceMinor = initialBalanceMinor,
    isArchived = isArchived,
    createdAtEpochMillis = createdAt.toEpochMilli(),
    updatedAtEpochMillis = updatedAt.toEpochMilli(),
)
