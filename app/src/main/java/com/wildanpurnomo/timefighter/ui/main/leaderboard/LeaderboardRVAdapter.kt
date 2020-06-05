package com.wildanpurnomo.timefighter.ui.main.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.data.leaderboard.LeaderboardModel
import kotlinx.android.synthetic.main.item_row_leaderboard.view.*

class LeaderboardRVAdapter : RecyclerView.Adapter<LeaderboardRVAdapter.ViewHolder>() {

    private val dataset: ArrayList<LeaderboardModel> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderboardRVAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_leaderboard, parent, false)
        )
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: LeaderboardRVAdapter.ViewHolder, position: Int) {
        holder.bind(dataset[position], position)
    }

    fun updateDataset(dataset: ArrayList<LeaderboardModel>) {
        if (!this.dataset.isNullOrEmpty()) dataset.clear()
        this.dataset.addAll(dataset)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            leaderboardModel: LeaderboardModel,
            position: Int
        ) {
            with(itemView) {
                itemLeaderboardName.text =
                    String.format("Rank #${position + 1}: ${leaderboardModel.username}")

                itemLeaderboardScore.text = String.format("Score: ${leaderboardModel.score}")
            }
        }
    }
}