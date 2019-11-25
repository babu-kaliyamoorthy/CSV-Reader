package com.rabobank.csvreader

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

/**
 * Created by Babu Kaliyamoorthy on 24/11/19.
 */
@RunWith(JUnit4::class)
class CommonUtilsTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val fileName = "issues.csv"

    @Mock
    private lateinit var context: Application

    /**
    This method will run before all tests.
     */
    @Before
    fun setUp() {
        /*
        we will initialize all mocked elements with this function.
         */
        // Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
    }

    @Test
    fun checkApplicationContextIsAvailable() {
    //TODO
        //      Assert.assertNotNull(CommonUtils.getApplicationContext())
    }

    @Test
    fun checkIfInputStreamIsReceiving() {
        //Assert.assertNotNull(CommonUtils.getInputStreamFromFile(context, fileName))
        //TODO
    }

}
