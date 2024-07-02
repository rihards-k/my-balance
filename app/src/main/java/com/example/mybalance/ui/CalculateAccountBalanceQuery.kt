package com.example.mybalance.ui

import com.example.mybalance.domain.Transaction
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculateAccountBalanceQuery @Inject constructor() {

    operator fun invoke(transactions: List<Transaction>): String {
        return transactions
            .sumOf { transaction ->
                transaction.amount.toDoubleOrNull() ?: 0.0
            }
            .round()
    }

    //Rounds decimal to the highest value of the 2 digits after decimal.
    private fun Double.round(): String {
        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.roundingMode = RoundingMode.CEILING
        return decimalFormat
            .format(this)
    }
}