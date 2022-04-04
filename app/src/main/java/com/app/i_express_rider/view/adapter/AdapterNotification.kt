package com.app.i_express_rider.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.i_express_rider.R
import com.bumptech.glide.Glide

class AdapterNotification(val context: Context?) :
    RecyclerView.Adapter<AdapterNotification.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note = itemView.findViewById<TextView>(R.id.note);
        val time = itemView.findViewById<TextView>(R.id.timestmp);
        val thumb = itemView.findViewById<ImageView>(R.id.thumb);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_notification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        if (position % 2 == 0) {
            Glide.with(context!!)
                .asDrawable()
                .load(R.drawable.ic_error)
                .into(holder.thumb)
        } else {
            Glide.with(context!!)
                .asDrawable()
                .load(R.drawable.ic_success)
                .into(holder.thumb)
        }
    }

    override fun getItemCount(): Int {
        return 10;
    }
}