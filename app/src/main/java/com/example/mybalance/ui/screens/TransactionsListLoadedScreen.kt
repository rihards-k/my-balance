package com.example.mybalance.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybalance.features.transactionslist.UiState
import com.example.mybalance.ui.TransactionUiItem

//TODO Move string constants to string resources.
@Composable
fun TransactionsListLoadedScreen(
    state: UiState.Loaded,
    onItemClick: (String) -> Unit
) {
    val currentAccountBalance = state.currentAccountBalance
    val transactions = state.transactionsList

    Text(
        modifier = Modifier.padding(8.dp),
        text = "Current account balance: $currentAccountBalance",
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    )

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = transactions.size
        ) { index ->
            val item = transactions[index]
            TransactionListItem(
                item = item,
                onClick = onItemClick
            )
        }
    }
}

@Composable
private fun TransactionListItem(
    item: TransactionUiItem,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick(item.id) }
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Counter party name: ${item.counterPartyName}",
            fontSize = 12.sp,
        )
        Text(
            text = "Date: ${item.date}",
            fontSize = 12.sp,
        )
        Text(
            text = "Amount: ${item.amount}",
            fontSize = 12.sp,
        )
    }
}