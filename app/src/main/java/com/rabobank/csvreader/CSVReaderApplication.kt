package com.rabobank.csvreader

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
import android.app.Application
import android.content.Context

class CSVReaderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {

        lateinit var appContext: Context

    }
}