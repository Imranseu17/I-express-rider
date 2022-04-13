package com.app.i_express_rider.view.adapter

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.app.i_express_rider.Model.models.Datum
import com.app.i_express_rider.R
import com.app.i_express_rider.view.ui.ChatActivity
import com.app.i_express_rider.view.ui.delivery.OrderDetailsBottomSheet
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class AdapterParcel() :
    RecyclerView.Adapter<AdapterParcel.HistoryViewHolder>() {

    private lateinit var shipmentAssignedList:List<Datum>
    private lateinit var context:Context
    private lateinit var orientation: ORIENTATION

    constructor(context: Context,shipmentAssignedList: List<Datum>,
                orientation: ORIENTATION)
            : this() {
        this.context = context
        this.shipmentAssignedList = shipmentAssignedList
        this.orientation = orientation

    }

    enum class ORIENTATION {
        VERTICAL, HORIZONTAL
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tracking_code = itemView.findViewById<TextView>(R.id.tracking_code)
        val dateTime = itemView.findViewById<TextView>(R.id.created_at)
        val sender_address = itemView.findViewById<TextView>(R.id.sender_address)
        val receiver_address = itemView.findViewById<TextView>(R.id.receiver_address)
        val statusText = itemView.findViewById<Chip>(R.id.status)
        val textView = itemView.findViewById<TextView>(R.id.note)
        val time = itemView.findViewById<TextView>(R.id.timestmp)
        val call_action = itemView.
            findViewById<ExtendedFloatingActionButton>(R.id.call_action)
        val chat_action = itemView.findViewById<ExtendedFloatingActionButton>(R.id.chat_action)

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

            // SharedPreferences

            val pref = context. getSharedPreferences("MyPref", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = pref.edit()
            editor.putString("sender_name","IGEN Limited")
            editor.putString("sender_address",shipmentAssignedList[position].
            senderAddress?.apartment+shipmentAssignedList[position].
            senderAddress?.country+shipmentAssignedList[position].
            senderAddress?.district+shipmentAssignedList[position].
            senderAddress?.division+shipmentAssignedList[position].
            senderAddress?.thana)
            editor.putString("sender_phone_number",shipmentAssignedList[position].senderContact?.phone)
           editor.putString("item_description",shipmentAssignedList[position].parcels[position].
           items[position].item_description)
            editor.putInt("item_weight",shipmentAssignedList[position].parcels[position].
            items[position].item_weight)
            editor.putInt("item_volume",shipmentAssignedList[position].parcels[position].
            items[position].item_volume)
            editor.putInt("worth",shipmentAssignedList[position].parcels[position].worth)
            editor.putInt("collection_amount",shipmentAssignedList[position].collectionAmount!!)
            editor.putString("receiver_name",shipmentAssignedList[position].receiverName)
            editor.putString("receiver_address",shipmentAssignedList[position].
            receiverAddress?.apartment+shipmentAssignedList[position].
            receiverAddress?.country+shipmentAssignedList[position].
            receiverAddress?.district+shipmentAssignedList[position].
            receiverAddress?.division+shipmentAssignedList[position].
            receiverAddress?.thana)
            editor.putString("receiver_phone_number",shipmentAssignedList[position].receiverContact?.phone)
            editor.commit()
            editor.apply()


        }


        holder.chat_action.setOnClickListener {
           context.startActivity(Intent(context,ChatActivity::class.java))
         }

        holder.tracking_code.setText(shipmentAssignedList[position].prefix+
            shipmentAssignedList[position].trackingCode +
            shipmentAssignedList[position].postfix)

        holder.dateTime.setText(shipmentAssignedList[position].createdAt)

        holder.sender_address.setText(shipmentAssignedList[position].
        senderAddress?.apartment+shipmentAssignedList[position].
        senderAddress?.country+shipmentAssignedList[position].
        senderAddress?.district+shipmentAssignedList[position].
        senderAddress?.division+shipmentAssignedList[position].
        senderAddress?.thana)

        holder.receiver_address.setText(shipmentAssignedList[position].
        receiverAddress?.apartment+shipmentAssignedList[position].
        receiverAddress?.country+shipmentAssignedList[position].
        receiverAddress?.district+shipmentAssignedList[position].
        receiverAddress?.division+shipmentAssignedList[position].
        receiverAddress?.thana)



        if(shipmentAssignedList[position]
                .statuses[position].type.equals("delivery")){
            holder.statusText.setText(shipmentAssignedList[position]
                .statuses[position].name)

        }

        holder.call_action.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:"+shipmentAssignedList[position]
                .senderContact?.phone.toString())
            context.startActivity(callIntent)
        }



    }

    override fun getItemCount(): Int {
        return shipmentAssignedList.size
    }

}