package com.rabobank.csvreader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rabobank.csvreader.databinding.IssueListItemBinding
import com.rabobank.csvreader.model.IssueDetail
import com.rabobank.csvreader.view.IssuesListActivity

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class IssueListAdapter(
    private val context: IssuesListActivity,
    private var issuesList: List<IssueDetail>
) : RecyclerView.Adapter<IssueListAdapter.IssueListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueListItemViewHolder {

        val inflater = LayoutInflater.from(context)
        val binding = IssueListItemBinding.inflate(inflater, parent, false)
        return IssueListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return issuesList.size
    }

    override fun onBindViewHolder(holder: IssueListItemViewHolder, position: Int) =
        holder.bind(issuesList[position])


    class IssueListItemViewHolder(private val binding: IssueListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: IssueDetail) {
            with(binding) {

                binding.csvFileHeaders = IssueDetail.Headers
                binding.listItemDta = item
                binding.executePendingBindings()
            }
        }
    }

    fun update(issueList: List<IssueDetail>) {
        issuesList = issueList
        notifyDataSetChanged()
    }
}
