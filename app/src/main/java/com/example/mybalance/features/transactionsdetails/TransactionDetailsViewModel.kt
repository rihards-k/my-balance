package com.example.mybalance.features.transactionsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybalance.domain.TransactionsRepository
import com.example.mybalance.ui.CreateTransactionUiItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = TransactionDetailsViewModel.TransactionDetailsViewModelFactory::class)
class TransactionDetailsViewModel @AssistedInject constructor(
    transactionsRepository: TransactionsRepository,
    private val createTransactionUiItem: CreateTransactionUiItem,
    @Assisted private val transactionId: String
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    val uiState = _uiState
        .asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _uiState.value
        )

    init {
        transactionsRepository.getTransaction(transactionId)
            .onEach { transaction ->
                _uiState.value = UiState.Loaded(createTransactionUiItem(transaction))
            }
            .catch {
                _uiState.value = UiState.Error
            }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    interface TransactionDetailsViewModelFactory {
        fun create(transactionId: String): TransactionDetailsViewModel
    }
}