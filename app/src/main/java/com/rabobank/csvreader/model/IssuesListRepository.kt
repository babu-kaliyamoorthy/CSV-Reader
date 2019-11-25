package com.rabobank.csvreader.model

import com.rabobank.csvreader.utils.CSVParser

const val CSV_FILE_NAME = "issues.csv"

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class IssuesListRepository : IssueDataSource {
    override fun fetchIssuesList(): ArrayList<IssueDetail> {
        var issuesList: ArrayList<IssueDetail>?
        val csvParser = CSVParser()
        issuesList = csvParser.parse(CSV_FILE_NAME)
        return issuesList
    }
}