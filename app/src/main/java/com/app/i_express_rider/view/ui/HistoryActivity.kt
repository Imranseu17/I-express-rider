package com.app.i_express_rider.view.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityHistoryBinding
import com.app.i_express_rider.view.adapter.AdapterHistory
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val datePicker =
        MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select date")
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    datePicker.show(supportFragmentManager, "MaterialDatePicker")
                    val simpleDateFormat = SimpleDateFormat("dd MMM yy", Locale.getDefault())
                    datePicker.addOnPositiveButtonClickListener {
                        with(it) {
                            val first: String = simpleDateFormat.format(this.first)
                            val second: String = simpleDateFormat.format(this.second)
                            binding.rangeChip.text = "$first to $second"
                            binding.rangeChip.visibility = View.VISIBLE
                        }
                    }
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }

        binding.rangeChip.setOnCloseIconClickListener {
            binding.rangeChip.visibility = View.GONE
            Toast.makeText(this, getString(R.string.date_range_clear), Toast.LENGTH_SHORT).show()
        }

        binding.rvHistory.visibility = View.GONE
        binding.rvHistory.adapter = AdapterHistory()
        binding.rvHistory.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                binding.shimmer.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
            }, 1000
        )
    }
}