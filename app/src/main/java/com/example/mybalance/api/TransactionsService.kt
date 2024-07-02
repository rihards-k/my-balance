package com.example.mybalance.api

import retrofit2.http.GET

interface TransactionsService {

    @GET("transactions")
    suspend fun transactions(): List<TransactionResponse>

    companion object {
        const val BASE_URL = "https://6419a452c152063412c7cf05.mockapi.io/"
    }
}