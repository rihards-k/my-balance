package com.example.mybalance.domain

import com.example.mybalance.database.TransactionDb

data class Transaction(
    val counterPartyName: String,
    val counterPartyAccount: String,
    val type: String,
    val amount: String,
    val description: String,
    val date: String,
    val id: String
) {
    companion object {
        fun Transaction.toTransactionDb(): TransactionDb {
            return TransactionDb(
                counterPartyName = this.counterPartyName,
                counterPartyAccount = this.counterPartyAccount,
                type = this.type,
                amount = this.amount,
                description = this.description,
                date = this.date,
                id = this.id
            )
        }
    }
}