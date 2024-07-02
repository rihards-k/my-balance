package com.example.mybalance.features.transactionsdetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mybalance.features.transactionslist.TransactionsListActivity.Companion.TRANSACTION_ID
import com.example.mybalance.ui.components.TopBar
import com.example.mybalance.ui.screens.ErrorScreen
import com.example.mybalance.ui.screens.LoadingScreen
import com.example.mybalance.ui.screens.TransactionsDetailsLoadedScreen
import com.example.mybalance.ui.theme.MyBalanceTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class TransactionDetailsActivity : ComponentActivity() {

    private val viewModel: TransactionDetailsViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<TransactionDetailsViewModel.TransactionDetailsViewModelFactory> { factory ->
                    factory.create(intent.getStringExtra(TRANSACTION_ID) ?: error("Transaction id must be provided!"))
                }
        }
    )

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Build UI
        setContent {
            MyBalanceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar() },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        Column {
                            val state: UiState by viewModel.uiState.collectAsStateWithLifecycle()
                            when (state) {
                                UiState.Error -> ErrorScreen()
                                is UiState.Loaded -> TransactionsDetailsLoadedScreen(state = state as UiState.Loaded)
                                UiState.Loading -> LoadingScreen()
                            }
                        }
                    }
                }
            }
        }
    }


}