package com.example.mybalance.features.transactionslist

import com.example.mybalance.ui.TransactionUiItem

sealed class UiState {

    data object Loading : UiState()

    data class Loaded(
        val currentAccountBalance: String,
        val transactionsList: List<TransactionUiItem>
    ) : UiState()

    data object EmptyData : UiState()

    data object Error : UiState()
}