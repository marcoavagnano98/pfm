package com.marco.pfm.domain.usecase

import com.marco.pfm.domain.model.TransactionType
import org.junit.Test

class TransactionValidationTest {
    @Test
    fun `income requires destination account only`() {
        validateTransaction(
            type = TransactionType.Income,
            amountMinor = 100,
            sourceAccountId = null,
            destinationAccountId = 1,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `income rejects source account`() {
        validateTransaction(
            type = TransactionType.Income,
            amountMinor = 100,
            sourceAccountId = 1,
            destinationAccountId = 2,
        )
    }

    @Test
    fun `expense requires source account only`() {
        validateTransaction(
            type = TransactionType.Expense,
            amountMinor = 100,
            sourceAccountId = 1,
            destinationAccountId = null,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `expense rejects destination account`() {
        validateTransaction(
            type = TransactionType.Expense,
            amountMinor = 100,
            sourceAccountId = 1,
            destinationAccountId = 2,
        )
    }

    @Test
    fun `transfer requires distinct accounts`() {
        validateTransaction(
            type = TransactionType.Transfer,
            amountMinor = 100,
            sourceAccountId = 1,
            destinationAccountId = 2,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `transfer rejects same account`() {
        validateTransaction(
            type = TransactionType.Transfer,
            amountMinor = 100,
            sourceAccountId = 1,
            destinationAccountId = 1,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `transaction rejects zero amount`() {
        validateTransaction(
            type = TransactionType.Expense,
            amountMinor = 0,
            sourceAccountId = 1,
            destinationAccountId = null,
        )
    }
}
