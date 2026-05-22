package com.marco.pfm.ui.features.accounts

import com.marco.pfm.domain.model.AccountType

val AccountType.label: String
    get() = when (this) {
        AccountType.Cash -> "Cash"
        AccountType.Bank -> "Bank"
        AccountType.Savings -> "Savings"
        AccountType.CreditCard -> "Credit card"
        AccountType.Investment -> "Investment"
        AccountType.Other -> "Other"
    }
