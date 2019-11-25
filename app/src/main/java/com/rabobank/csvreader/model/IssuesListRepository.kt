package com.rabobank.csvreader.model

import com.rabobank.csvreader.utils.CSVParser
import java.io.InputStream

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class IssuesListRepository(
    private val csvParser: CSVParser,
    private val inputStream: InputStream
) : IssueDataSource {
    override fun fetchIssuesList(): ArrayList<IssueDetail> {
        var issuesList: ArrayList<IssueDetail>?
        issuesList = csvParser.parse(inputStream)
        return issuesList
    }
}