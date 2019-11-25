package com.rabobank.csvreader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rabobank.csvreader.base.CoroutinesContextProvider
import com.rabobank.csvreader.model.IssueDataSource
import com.rabobank.csvreader.model.IssueDetail
import com.rabobank.csvreader.model.IssuesListViewState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class IssuesListViewModel(
    private val repository: IssueDataSource,
    contextProvider: CoroutinesContextProvider
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    private val _issuesList = MutableLiveData<List<IssueDetail>>().apply { value = emptyList() }
    val issuesList: LiveData<List<IssueDetail>> = _issuesList

    private val _isFetchInProgress = MutableLiveData<Boolean>()
    val isFetchInProgress: LiveData<Boolean> = _isFetchInProgress

    private val _isExceptionOccurred = MutableLiveData<Boolean>()
    val isExceptionOccurred: LiveData<Boolean> = _isExceptionOccurred

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    /**
     *  Coroutine exception handler for catching the exception
     */
    private val handler = CoroutineExceptionHandler { _, exception ->
        _isExceptionOccurred.postValue(true)
        _isFetchInProgress.postValue(false)

        issuesListViewState.value = IssuesListViewState.Error(exception)
    }

    private val issuesListViewState = MutableLiveData<IssuesListViewState>()

    fun getStateLiveData(): LiveData<IssuesListViewState> = issuesListViewState

    /**
     * We use coroutine to do the parsing in background thread.
     */
    fun fetchIssuesListDataFromFile() {

        issuesListViewState.value = IssuesListViewState.IsParsingInProgress

        launch(handler) {
            _isFetchInProgress.postValue(true)
            parseIssueList()
            _isFetchInProgress.postValue(false)
        }
    }

    /**
     * Function to handle suspend/Resume/Pause
     */
    private suspend fun parseIssueList() {

        val issuesListResponse: ArrayList<IssueDetail> = withContext(Dispatchers.IO) {
            repository.fetchIssuesList()
        }
        if (issuesListResponse.isEmpty()) {
            _isEmptyList.postValue(true)
        } else
            _issuesList.value = issuesListResponse

        issuesListViewState.value = IssuesListViewState.Success(issuesListResponse)
    }

    /**
     * Function to clear the jobs and this method will be called when this ViewModel is no longer
     * used and will be destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}
