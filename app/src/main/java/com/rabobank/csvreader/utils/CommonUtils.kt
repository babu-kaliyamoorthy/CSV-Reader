package com.rabobank.csvreader.utils

import android.content.Context
import com.rabobank.csvreader.CSVReaderApplication
import java.io.InputStream

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class CommonUtils {
    companion object {
        fun getApplicationContext(): Context = CSVReaderApplication.appContext

        fun getInputStreamFromFile(context: Context, fileName: String): InputStream =
            context.assets.open(fileName)
    }
}