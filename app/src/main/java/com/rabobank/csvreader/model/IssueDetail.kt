package com.rabobank.csvreader.model

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
data class IssueDetail(
    var firstName: String? = null,
    var surName: String? = null,
    var issuesCount: String? = null,
    var dateOfBirth: String? = null
) {
    companion object Headers {
        var firstHeader: String? = null
        var secondHeader: String? = null
        var thirdHeader: String? = null
        var fourthHeader: String? = null
    }
}
