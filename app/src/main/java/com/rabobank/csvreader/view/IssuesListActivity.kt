package com.rabobank.csvreader.view


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rabobank.csvreader.R
import com.rabobank.csvreader.adapter.IssueListAdapter
import com.rabobank.csvreader.base.CoroutinesContextProvider
import com.rabobank.csvreader.databinding.ActivityIssueListBinding
import com.rabobank.csvreader.model.IssueDetail
import com.rabobank.csvreader.model.IssuesListRepository
import com.rabobank.csvreader.utils.CSVParser
import com.rabobank.csvreader.utils.CommonUtils
import com.rabobank.csvreader.viewmodel.IssuesListViewModel
import com.rabobank.csvreader.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_issue_list.*

const val CSV_FILE_NAME = "issues.csv"

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class IssuesListActivity : AppCompatActivity() {
    private lateinit var issuesListViewModel: IssuesListViewModel
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var issueListAdapter: IssueListAdapter
    private lateinit var binding: ActivityIssueListBinding

    companion object {
        val TAG = IssuesListActivity::class.java.simpleName
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_issue_list)

        initializeIssuesListViewModel()
        initializeIssueListAdapter()
        binding.issueListsViewModel = issuesListViewModel
        binding.lifecycleOwner = this

    }

    /**
     * This method is used for initializing view model and call view model method for parsing.
     */
    private fun initializeIssuesListViewModel() {
        Log.d(TAG, "initializeIssuesListViewModel method called")
        var inputStream =
            CommonUtils.getInputStreamFromFile(CommonUtils.getApplicationContext(), CSV_FILE_NAME)

        issuesListViewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                IssuesListRepository(CSVParser(), inputStream),
                CoroutinesContextProvider()
            )
        )
            .get(IssuesListViewModel::class.java)
        issuesListViewModel.issuesList.observe(this, fetchIssuesList)
        getIssueList()

    }

    /**
     * This method is used to do a normal static set up for recyclerview and adapter.
     */
    private fun initializeIssueListAdapter() {
        Log.d(TAG, "initializeIssueListAdapter method called")
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        issueListAdapter =
            IssueListAdapter(this, issuesListViewModel.issuesList.value ?: emptyList())
        recycler_view.adapter = issueListAdapter
    }

    /**
     * This method is used for calling view model method to do parsing and return as list .
     */
    private fun getIssueList() {
        Log.d(TAG, "get issues List method called")
        if (issuesListViewModel != null && issuesListViewModel.issuesList.value?.size == 0) {
            issuesListViewModel.fetchIssuesListDataFromFile()
        }
    }


    /**
     * This val  is observer field.Whenever the data is changed
     * the list will be automatically updated by observing observable using this field.
     */
    private val fetchIssuesList = Observer<List<IssueDetail>> {
        issueListAdapter.update(it)
    }
}
