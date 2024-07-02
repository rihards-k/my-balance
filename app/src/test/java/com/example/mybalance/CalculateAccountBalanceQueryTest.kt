package com.example.mybalance

import com.example.mybalance.domain.Transaction
import com.example.mybalance.ui.CalculateAccountBalanceQuery
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class CalculateAccountBalanceQueryTest {

    private lateinit var subject: CalculateAccountBalanceQuery

    @Before
    fun setUp() {
        subject = CalculateAccountBalanceQuery()
    }

    @Test
    fun `Total account balance is correctly calculated`() {
        val expectedResult = "1154.74"
        assertEquals(expectedResult, subject.invoke(createTransactions()))
    }

    //TODO Add additional test cases tests

    private fun createTransactions(): List<Transaction> {
        return listOf(
            Transaction(
                counterPartyName = "",
                counterPartyAccount = "",
                type = "",
                amount = "12.67",
                description = "",
                date = "",
                id = ""
            ),
            Transaction(
                counterPartyName = "",
                counterPartyAccount = "",
                type = "",
                amount = "679.561111",
                description = "",
                date = "",
                id = ""
            ),
            Transaction(
                counterPartyName = "",
                counterPartyAccount = "",
                type = "",
                amount = "462.49999",
                description = "",
                date = "",
                id = ""
            )
        )
    }
}