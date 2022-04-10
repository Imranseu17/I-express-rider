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

class AdapterChat(val context: Context, val orientation: ORIENTATION = ORIENTATION.VERTICAL) :
    RecyclerView.Adapter<AdapterChat.ChatViewHolder>() {

    enum class ORIENTATION {
        VERTICAL
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textincomessage = itemView.findViewById<TextView>(R.id.text_message_incoming)
        val time = itemView.findViewById<TextView>(R.id.timestmp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {

        lateinit var view: View

            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_parcel_chat_card, parent, false)


        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            OrderDetailsBottomSheet().show(
                (context as AppCompatActivity).supportFragmentManager,
                OrderDetailsBottomSheet.TAG
            )
        }

        if(position%2 == 0){
            holder.textincomessage.setBackgroundResource(R.drawable.sender_chat_card)
        }else
        {
            holder.textincomessage.setBackgroundResource(R.drawable.outgoing_spech_card)
        }


    }

    override fun getItemCount(): Int {
        return 10
    }

}