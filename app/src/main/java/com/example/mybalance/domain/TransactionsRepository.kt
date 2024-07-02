package com.example.mybalance.domain

import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun getTransactions(): Flow<List<Transaction>>

    fun getTransaction(id: String): Flow<Transaction>
}