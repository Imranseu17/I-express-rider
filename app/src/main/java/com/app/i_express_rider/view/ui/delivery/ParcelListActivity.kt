package com.app.i_express_rider.view.ui.delivery

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.i_express_rider.databinding.ActivityParcelListBinding
import com.app.i_express_rider.view.adapter.AdapterParcel
import com.app.i_express_rider.view.utils.Constant
import com.app.i_express_rider.viewmodel.DataViewModel

class ParcelListActivity : AppCompatActivity() {
    private val TAG = "NewParcelActivity"

    private lateinit var binding: ActivityParcelListBinding
    private var dataViewModel: DataViewModel = DataViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParcelListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parcelType = intent.extras?.getString(Constant.ParcelListModeKey)
        if (parcelType.equals(Constant.ParcelListMode_ACEEPTED)){
            supportActionBar?.title = "Your parcel"
        } else{
            supportActionBar?.title = "New parcel"
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.rvNewParcel.visibility = View.GONE
        binding.rvNewParcel.adapter = AdapterParcel(this)
        binding.rvNewParcel.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                binding.shimmer.visibility = View.GONE
                binding.rvNewParcel.visibility = View.VISIBLE
            }, 2000
        )

        DataViewModel.selected.observe(this, Observer {
            Log.d(TAG, "observe: $it")
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}