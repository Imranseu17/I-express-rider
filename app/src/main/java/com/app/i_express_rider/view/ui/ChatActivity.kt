package com.app.i_express_rider.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.i_express_rider.R
import com.app.i_express_rider.view.adapter.AdapterChat

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)

        val recyclerview = findViewById<RecyclerView>(R.id.chat_list)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)



        // This will pass the ArrayList to our Adapter
        val adapter = AdapterChat(this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


    }
}