package com.rabobank.csvreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rabobank.csvreader.base.CoroutinesContextProvider
import com.rabobank.csvreader.model.IssueDataSource

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class ViewModelFactory(
    private val repository: IssueDataSource,
    private val contextProvider: CoroutinesContextProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IssuesListViewModel(repository, contextProvider) as T
    }
}