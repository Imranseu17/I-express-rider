package com.app.i_express_rider.view.ui.delivery

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.i_express_rider.databinding.ActivityFeedbackBinding
import com.app.i_express_rider.viewmodel.DataViewModel

class FeedbackActivity : AppCompatActivity() {
    val TAG = "FeedbackActivity"
    private var dataViewModel: DataViewModel = DataViewModel()
    lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val reasonList = listOf("Customer unreachable", "Area", "Others")
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, reasonList)
        binding.reason.setAdapter(adapter)
        binding.reason.setOnItemClickListener { parent, view, position, id ->
            Log.d(TAG, "onCreate: OnItemClickListener")
        }

        binding.btnConfirm.setOnClickListener {
            Toast.makeText(this, "Saved your feedback for future investigation", Toast.LENGTH_SHORT).show()
            DataViewModel.selected.value = true
            onBackPressed()
        }
    }
}