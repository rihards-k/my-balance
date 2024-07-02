package com.example.mybalance.api

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("counterPartyName") val counterPartyName: String,
    @SerializedName("counterPartyAccount") val counterPartyAccount: String,
    @SerializedName("type") val type: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: String,
    @SerializedName("id") val id: String
)
