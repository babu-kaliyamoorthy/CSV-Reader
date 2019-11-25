package com.rabobank.csvreader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rabobank.csvreader.model.IssueDataSource
import com.rabobank.csvreader.model.IssueDetail
import com.rabobank.csvreader.model.IssuesListViewState
import com.rabobank.csvreader.viewmodel.IssuesListViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

/**
 * Created by Babu Kaliyamoorthy on 24/11/19.
 */
@RunWith(JUnit4::class)
class IssuesListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var issuesListViewModel: IssuesListViewModel

    @Mock
    lateinit var issueDataSource: IssueDataSource

    @Mock
    lateinit var viewStateObserver: Observer<IssuesListViewState>

    @Mock
    private lateinit var isFetchInProgressObserver: Observer<Boolean>
    private lateinit var onExceptionOccurredObserver: Observer<Any>
    private lateinit var emptyIssuesListObserver: Observer<Boolean>
    private lateinit var onSuccessfulParseIssuesListObserver: Observer<List<IssueDetail>>
    private lateinit var issuesList: ArrayList<IssueDetail>

    @Before
    fun setUp() {
        /*
        we will initialize all mocked elements with this function.
         */
        MockitoAnnotations.initMocks(this)
        initializeObservers()
        createMockIssueList()

        issuesListViewModel = IssuesListViewModel(issueDataSource, TestContextProvider()).apply {
            getStateLiveData().observeForever(viewStateObserver)
            isFetchInProgress.observeForever(isFetchInProgressObserver)
            isExceptionOccurred.observeForever(onExceptionOccurredObserver)
            isEmptyList.observeForever(emptyIssuesListObserver)
            issuesList.observeForever(onSuccessfulParseIssuesListObserver)
        }
    }

    @Test
    fun issuesParsingEmptyListResponse() = testCoroutineRule.runBlockingTest {

        val emptyIssuesList = ArrayList<IssueDetail>()
        whenever(issueDataSource.fetchIssuesList()).thenReturn(emptyIssuesList)
        issuesListViewModel.fetchIssuesListDataFromFile()
        verify(viewStateObserver).onChanged(IssuesListViewState.IsParsingInProgress)
        verify(viewStateObserver).onChanged(IssuesListViewState.Success(emptyIssuesList))
        Assert.assertNotNull(issuesListViewModel.isFetchInProgress.value)
        Assert.assertNull(issuesListViewModel.isExceptionOccurred.value)
        Assert.assertNotNull(issuesListViewModel.isEmptyList.value)
        Assert.assertTrue(issuesListViewModel.isEmptyList.value == true)
        Assert.assertTrue(issuesListViewModel.issuesList.value?.size == 0)
    }

    @Test
    fun issuesParsingSuccessFulResponse() = testCoroutineRule.runBlockingTest {

        whenever(issueDataSource.fetchIssuesList()).thenReturn(issuesList)
        issuesListViewModel.fetchIssuesListDataFromFile()
        verify(viewStateObserver).onChanged(IssuesListViewState.IsParsingInProgress)
        verify(viewStateObserver).onChanged(IssuesListViewState.Success(issuesList))

        Assert.assertNotNull(issuesListViewModel.isFetchInProgress.value)
        Assert.assertNull(issuesListViewModel.isExceptionOccurred.value)
        Assert.assertNull(issuesListViewModel.isEmptyList.value)
        Assert.assertTrue(issuesListViewModel.issuesList.value?.size == 4)
    }

    @Test
    fun issuesParsingFailedResponse() =
        testCoroutineRule.runBlockingTest {
            val error = Error()
            whenever(issueDataSource.fetchIssuesList()).thenThrow(error)
            issuesListViewModel.fetchIssuesListDataFromFile()
            verify(viewStateObserver).onChanged(IssuesListViewState.IsParsingInProgress)
            Assert.assertNotNull(issuesListViewModel.isFetchInProgress.value)
            Assert.assertNull(issuesListViewModel.isEmptyList.value)
            Assert.assertTrue(issuesListViewModel.issuesList.value?.size == 0)
        }


    private fun initializeObservers() {
        isFetchInProgressObserver = mock(Observer::class.java) as Observer<Boolean>
        onExceptionOccurredObserver = mock(Observer::class.java) as Observer<Any>
        emptyIssuesListObserver = mock(Observer::class.java) as Observer<Boolean>
        onSuccessfulParseIssuesListObserver =
            mock(Observer::class.java) as Observer<List<IssueDetail>>
    }

    private fun createMockIssueList() {
        issuesList = ArrayList()
        issuesList.add(IssueDetail("John", "Micheal", "10", "1978-01-02T00:00:00"))
        issuesList.add(IssueDetail("John", "Micheal", "10", "1978-01-02T00:00:00"))
        issuesList.add(IssueDetail("John", "Micheal", "10", "1978-01-02T00:00:00"))
        issuesList.add(IssueDetail("John", "Micheal", "10", "1978-01-02T00:00:00"))
    }

}