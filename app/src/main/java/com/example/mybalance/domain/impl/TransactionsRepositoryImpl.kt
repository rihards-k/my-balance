package com.example.mybalance.domain.impl

import com.example.mybalance.api.TransactionsService
import com.example.mybalance.database.TransactionDb.Companion.toTransaction
import com.example.mybalance.database.TransactionsDao
import com.example.mybalance.domain.Transaction
import com.example.mybalance.domain.Transaction.Companion.toTransactionDb
import com.example.mybalance.domain.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService,
    private val transactionsDao: TransactionsDao
) : TransactionsRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return flow {
            try {
                val response = transactionsService.transactions()
                val items = response.map { data ->
                    Transaction(
                        counterPartyName = data.counterPartyName,
                        counterPartyAccount = data.counterPartyAccount,
                        type = data.type,
                        amount = data.amount,
                        description = data.description,
                        date = data.date,
                        id = data.id
                    )
                }
                // Insert transactions into DB
                transactionsDao.insert(items.map { it.toTransactionDb() })
                emit(items)
            } catch (e: Exception) {
                //TODO handle different exceptions
                //If network request fails, just return transactions from DB
                val items = transactionsDao.getTransactions()
                    .map { it.toTransaction() }
                emit(items)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getTransaction(id: String): Flow<Transaction> {
        return transactionsDao.getTransaction(id)
            .map { it.toTransaction() }
            .flowOn(Dispatchers.IO)
    }

}