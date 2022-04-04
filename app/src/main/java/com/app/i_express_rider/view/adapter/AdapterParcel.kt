package com.app.i_express_rider.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.i_express_rider.R
import com.app.i_express_rider.view.ui.delivery.OrderDetailsBottomSheet

class AdapterParcel(val context: Context, val orientation: ORIENTATION = ORIENTATION.VERTICAL) :
    RecyclerView.Adapter<AdapterParcel.HistoryViewHolder>() {

    enum class ORIENTATION {
        VERTICAL, HORIZONTAL
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.note)
        val time = itemView.findViewById<TextView>(R.id.timestmp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {

        lateinit var view: View

        if (orientation == ORIENTATION.HORIZONTAL) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_parcel_land, parent, false)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_parcel, parent, false)
        }

        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            OrderDetailsBottomSheet().show(
                (context as AppCompatActivity).supportFragmentManager,
                OrderDetailsBottomSheet.TAG
            )
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

}