package com.example.mybalance.features.transactionslist

sealed class UiEvent {

    data class OpenDetails(val id: String) : UiEvent()
}