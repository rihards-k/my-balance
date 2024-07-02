package com.example.mybalance.ui

data class TransactionUiItem(
    val counterPartyName: String,
    val counterPartyAccount: String,
    val amount: String,
    val description: String,
    val date: String,
    val id: String
)