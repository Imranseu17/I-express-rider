package com.app.i_express_rider.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.i_express_rider.R

class AdapterLeaderboard : RecyclerView.Adapter<AdapterLeaderboard.LeaderboardViewHolder>() {

    inner class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        return LeaderboardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_leaderboard,parent, false))
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10;
    }
}