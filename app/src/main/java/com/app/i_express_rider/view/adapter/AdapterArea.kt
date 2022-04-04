package com.app.i_express_rider.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.i_express_rider.R

class AdapterArea : RecyclerView.Adapter<AdapterArea.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvArea = itemView.findViewById<TextView>(R.id.tvArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_area,parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10;
    }
}