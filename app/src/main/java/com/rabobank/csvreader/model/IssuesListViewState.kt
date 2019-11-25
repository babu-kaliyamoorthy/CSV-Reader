package com.rabobank.csvreader.model

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
sealed class IssuesListViewState {
    object IsParsingInProgress : IssuesListViewState()
    data class Error(val throwable: Throwable) : IssuesListViewState()
    data class Success(val issueslist: ArrayList<IssueDetail>) : IssuesListViewState()
}