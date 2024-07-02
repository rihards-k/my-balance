package com.example.mybalance.features.transactionslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybalance.domain.TransactionsRepository
import com.example.mybalance.ui.CalculateAccountBalanceQuery
import com.example.mybalance.ui.CreateTransactionUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsListViewModel @Inject constructor(
    transactionsRepository: TransactionsRepository,
    private val calculateAccountBalanceQuery: CalculateAccountBalanceQuery,
    private val createTransactionUiItem: CreateTransactionUiItem
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    private val _uiEvent = Channel<UiEvent>()

    val uiState = _uiState
        .asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _uiState.value
        )
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    init {
        transactionsRepository.getTransactions()
            .onEach { transactions ->
                if (transactions.isNotEmpty()) {
                    _uiState.value = UiState.Loaded(
                        currentAccountBalance = calculateAccountBalanceQuery(transactions),
                        transactionsList = transactions.map { createTransactionUiItem(it) }
                    )
                } else {
                    _uiState.value = UiState.EmptyData
                }
            }
            .onCompletion { cause ->
                if (cause != null) {
                    _uiState.value = UiState.EmptyData
                }
            }
            .catch {
                _uiState.value = UiState.Error
            }
            .launchIn(viewModelScope)
    }

    fun onItemClicked(id: String) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.OpenDetails(id))
        }
    }

}