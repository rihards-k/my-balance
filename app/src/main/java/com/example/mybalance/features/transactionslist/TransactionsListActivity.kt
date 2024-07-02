package com.example.mybalance.features.transactionslist

import android.content.Intent
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mybalance.features.transactionsdetails.TransactionDetailsActivity
import com.example.mybalance.ui.components.TopBar
import com.example.mybalance.ui.screens.EmptyDataScreen
import com.example.mybalance.ui.screens.ErrorScreen
import com.example.mybalance.ui.screens.LoadingScreen
import com.example.mybalance.ui.screens.TransactionsListLoadedScreen
import com.example.mybalance.ui.theme.MyBalanceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TransactionsListActivity : ComponentActivity() {

    private val viewModel: TransactionsListViewModel by viewModels()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Observe events from viewmodel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { uiEvent ->
                    when (uiEvent) {
                        is UiEvent.OpenDetails -> startDetailsActivity(uiEvent.id)
                    }
                }
            }
        }

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
                                UiState.EmptyData -> EmptyDataScreen()
                                UiState.Error -> ErrorScreen()
                                is UiState.Loaded -> TransactionsListLoadedScreen(
                                    state = state as UiState.Loaded,
                                    onItemClick = { viewModel.onItemClicked(it) }
                                )

                                UiState.Loading -> LoadingScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startDetailsActivity(transactionId: String) {
        val intent = Intent(this, TransactionDetailsActivity::class.java)
            .putExtra(TRANSACTION_ID, transactionId)
        startActivity(intent)
    }

    companion object {
        const val TRANSACTION_ID = "transactionId"
    }
}