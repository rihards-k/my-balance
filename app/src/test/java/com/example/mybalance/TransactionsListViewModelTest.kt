package com.example.mybalance

import app.cash.turbine.test
import com.example.mybalance.domain.TransactionsRepository
import com.example.mybalance.features.transactionslist.TransactionsListViewModel
import com.example.mybalance.features.transactionslist.UiState
import com.example.mybalance.ui.CalculateAccountBalanceQuery
import com.example.mybalance.ui.CreateTransactionUiItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainCoroutineRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionsListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var transactionsRepository: TransactionsRepository

    @Mock
    private lateinit var calculateAccountBalanceQuery: CalculateAccountBalanceQuery

    @Mock
    private lateinit var createTransactionUiItem: CreateTransactionUiItem

    private lateinit var subject: TransactionsListViewModel
    private lateinit var mocks: AutoCloseable

    @Before
    fun setUp() {
        mocks = openMocks(this)
        subject = TransactionsListViewModel(
            transactionsRepository,
            calculateAccountBalanceQuery,
            createTransactionUiItem
        )
    }

    @After
    fun tearDown() {
        mocks.close()
    }

    @Test
    fun `Loading State is emitted when launched`() = runTest {
        whenever(transactionsRepository.getTransactions())
            .thenReturn(emptyFlow())
        subject.uiState.test {
            assertEquals(awaitItem(), UiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    //TODO Add additional test cases tests
}