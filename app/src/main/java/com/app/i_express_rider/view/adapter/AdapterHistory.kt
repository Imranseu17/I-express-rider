package com.app.i_express_rider.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.i_express_rider.R

class AdapterHistory : RecyclerView.Adapter<AdapterHistory.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.note);
        val time = itemView.findViewById<TextView>(R.id.timestmp);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_parcel_history,parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10;
    }
}