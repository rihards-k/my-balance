package com.example.mybalance.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mybalance.domain.Transaction

@Entity(tableName = "transactions")
data class TransactionDb(
    val counterPartyName: String,
    val counterPartyAccount: String,
    val type: String,
    val amount: String,
    val description: String,
    val date: String,
    @PrimaryKey
    val id: String
) {
    companion object {
        fun TransactionDb.toTransaction(): Transaction {
            return Transaction(
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