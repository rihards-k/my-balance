package com.example.mybalance.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybalance.features.transactionsdetails.UiState

//TODO Move string constants to string resources.
@Composable
fun TransactionsDetailsLoadedScreen(state: UiState.Loaded) {
    val transaction = state.transaction

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Counter party name: ${transaction.counterPartyName}",
            fontSize = 12.sp,
        )
        Text(
            text = "Date: ${transaction.date}",
            fontSize = 12.sp,
        )
        Text(
            text = "Amount: ${transaction.amount}",
            fontSize = 12.sp,
        )

        Text(
            text = "Counter party account: ${transaction.counterPartyAccount}",
            fontSize = 12.sp,
        )

        Text(
            text = "Description: ${transaction.description}",
            fontSize = 12.sp,
        )
    }
}