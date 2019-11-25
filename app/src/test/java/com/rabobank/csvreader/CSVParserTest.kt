package com.rabobank.csvreader

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.rabobank.csvreader.model.IssueDetail
import com.rabobank.csvreader.utils.CSVParser
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @Mock
    private lateinit var csvParser1: CSVParser

    @Mock
    private lateinit var issueDetail: IssueDetail


    /**
    This method will run before all tests.
     */
    @Before
    fun setUp() {
        /**
        we will initialize all mocked elements with this function.
         */
        MockitoAnnotations.initMocks(this)
        doReturn(assetManager).`when`(applicationContext).assets
        applicationContext = mock(Context::class.java)
        customResources = mock(Resources::class.java)
        assetManager = mock(AssetManager::class.java)
        csvParser1 = mock(CSVParser::class.java)
        issueDetail = mock(IssueDetail::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun readCSVFileFromAssetFolderToCheckTheSuccessResponseIsComing() {
        var issuesList = createMockIssueList()

        //Given
        val inputStream = this.javaClass.classLoader!!.getResourceAsStream("issues.csv")
        whenever(csvParser1.parse(inputStream)).thenReturn(issuesList)

        //when
        csvParser1.parse(inputStream)

        //then
        assertThat(inputStream, notNullValue())
        assertThat(issuesList, notNullValue())
        assertTrue(issuesList.size == 4)
    }


    @Test
    @Throws(Exception::class)
    fun readCSVFileFromAssetFolderToCheckTheSuccessEmptyListResponseIsComing() {

        val emptyIssuesList = ArrayList<IssueDetail>()

        //Given
        val inputStream = this.javaClass.classLoader!!.getResourceAsStream("issues.csv")
        whenever(csvParser1.parse(inputStream)).thenReturn(emptyIssuesList)

        //when
        csvParser1.parse(inputStream)

        //then
        assertThat(inputStream, notNullValue())
        assertTrue(emptyIssuesList.isEmpty())
        assertTrue(emptyIssuesList.size == 0)
    }


    private fun createMockIssueList(): ArrayList<IssueDetail> {
        var issuesList = ArrayList<IssueDetail>()

        issuesList.add(IssueDetail("John", "Micheal", "10", "1978-01-02T00:00:00"))
        issuesList.add(IssueDetail("Ramesh", "Micheal", "10", "1978-01-02T00:00:00"))
        issuesList.add(IssueDetail("Micheal", "Micheal", "10", "1978-01-02T00:00:00"))
        issuesList.add(IssueDetail("Albert", "Micheal", "10", "1978-01-02T00:00:00"))

        return issuesList
    }


}