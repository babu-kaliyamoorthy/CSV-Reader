package com.rabobank.csvreader

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rabobank.csvreader.utils.CommonUtils
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.AdditionalMatchers.not
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


/**
 * Created by Babu Kaliyamoorthy on 24/11/19.
 */
class CSVParserTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var applicationContext: Context

    @Mock
    private lateinit var customResources: Resources

    @Mock
    private lateinit var assetManager: AssetManager


    /**
    This method will run before all tests.
     */
    @Before
    fun setUp() {
        /*
        we will initialize all mocked elements with this function.
         */
        MockitoAnnotations.initMocks(this)
        doReturn(assetManager).`when`(applicationContext).getAssets()
        /*applicationContext = mock(Context::class.java)
        customResources = mock(Resources::class.java)
        assetManager = mock(AssetManager::class.java)*/
    }

    @Test
    fun parseCSVFileFromAssetSuccessResponse() {
        CommonUtils.getInputStreamFromFile(applicationContext, "issues.csv")
        //TODO

    }

    @Test
    @Throws(Exception::class)
    fun test_play_once() {
//        assertThat(applicationContext.assets.open("issues.csv"), not(nullValue()))))
        Assert.assertNull(applicationContext.assets.open("issues.csv"))
    }

}