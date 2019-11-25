package com.rabobank.csvreader.model

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
interface IssueDataSource {
    fun fetchIssuesList(): ArrayList<IssueDetail>
}