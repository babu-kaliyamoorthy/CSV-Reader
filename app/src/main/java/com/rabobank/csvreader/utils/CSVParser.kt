package com.rabobank.csvreader.utils

import android.text.TextUtils
import com.opencsv.CSVReader
import com.rabobank.csvreader.model.IssueDetail
import java.io.InputStreamReader

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class CSVParser {
    /**
     * method to parse the CSV file using given file name in asset folder.
     */
    fun parse(csvFileName: String): ArrayList<IssueDetail> {

        val issuesList = ArrayList<IssueDetail>()
        var csvReader: CSVReader? = null
        var csvStreamReader: InputStreamReader?
        try {
            val inputStream =
                CommonUtils.getInputStreamFromFile(CommonUtils.getApplicationContext(), csvFileName)

            csvStreamReader = InputStreamReader(inputStream)
            csvReader = CSVReader(csvStreamReader)
            var record: Array<String>?

            record = csvReader.readNext()

            if (record != null) {

                readHeader(record)

                record = csvReader.readNext()
                while (record != null) {
                    var issue = IssueDetail()
                    val iterator = record.iterator()
                    for ((index, value) in iterator.withIndex()) {
                        when (index) {
                            0 -> issue.firstName = value
                            1 -> issue.surName = value
                            2 -> issue.issuesCount = value
                            3 -> issue.dateOfBirth = value
                            else -> "As of now we are not handling the this index!"
                        }
                    }

                    /*
                     We are not adding empty record to the list.So we check whether all the fields
                     in the record are empty
                     */
                    if (issue != null && (!TextUtils.isEmpty(issue.firstName)
                                || !TextUtils.isEmpty(issue.surName)
                                || !TextUtils.isEmpty(issue.issuesCount)
                                || !TextUtils.isEmpty(issue.dateOfBirth))
                    ) {
                        issuesList.add(issue)
                    }
                    record = csvReader.readNext()
                }

            }
        } catch (exception: Exception) {
            throw Exception()
        } finally {
            csvReader?.close()
        }
        return issuesList
    }


    /**
    This method is used to read the header values
     */
    private fun readHeader(record: Array<String>) {
        val iterator = record.iterator()
        for ((index, value) in iterator.withIndex()) {
            when (index) {
                0 -> IssueDetail.firstHeader = value
                1 -> IssueDetail.secondHeader = value
                2 -> IssueDetail.thirdHeader = value
                3 -> IssueDetail.fourthHeader = value
                else -> "As of now we are not handling the this index!"
            }
        }
    }
}

