package com.example.mybalance.features.transactionsdetails

import com.example.mybalance.ui.TransactionUiItem

sealed class UiState {

    data object Loading : UiState()

    data class Loaded(val transaction: TransactionUiItem) : UiState()

    data object Error : UiState()
}