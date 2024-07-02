package com.example.mybalance.ui

import com.example.mybalance.domain.Transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTransactionUiItem @Inject constructor() {

    operator fun invoke(transaction: Transaction): TransactionUiItem {
        return TransactionUiItem(
            counterPartyName = transaction.counterPartyName,
            counterPartyAccount = transaction.counterPartyAccount,
            amount = "${transaction.type} = ${transaction.amount}",
            description = transaction.description,
            date = transaction.date,
            id = transaction.id
        )
    }
}