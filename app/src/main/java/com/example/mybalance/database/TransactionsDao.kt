package com.example.mybalance.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactions: List<TransactionDb>)

    @Query("SELECT * from transactions")
    suspend fun getTransactions(): List<TransactionDb>

    @Query("SELECT * from transactions WHERE id = :id")
    fun getTransaction(id: String): Flow<TransactionDb>
}