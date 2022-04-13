package com.app.i_express_rider.view.ui.delivery

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.i_express_rider.Model.Presenter.ShipmentAssignedPresenter
import com.app.i_express_rider.Model.callback.ShipmentAssignedUserView
import com.app.i_express_rider.Model.models.Datum
import com.app.i_express_rider.Model.models.ShipmentAssigned
import com.app.i_express_rider.databinding.ActivityParcelListBinding
import com.app.i_express_rider.view.adapter.AdapterParcel
import com.app.i_express_rider.view.utils.Constant
import com.app.i_express_rider.viewmodel.DataViewModel

class ParcelListActivity : AppCompatActivity(),ShipmentAssignedUserView {
    private val TAG = "NewParcelActivity"

    private lateinit var binding: ActivityParcelListBinding
    private var dataViewModel: DataViewModel = DataViewModel()
    private lateinit var  shipmentAssignedList:List<Datum>
    private lateinit var shipmentAssignedPresenter: ShipmentAssignedPresenter

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

        binding.shimmer.visibility = View.VISIBLE
        binding.rvNewParcel.visibility = View.GONE

        val token:String? = intent.getStringExtra("token")

        shipmentAssignedPresenter = ShipmentAssignedPresenter(this)
        shipmentAssignedPresenter.
        attemptShipmentAssignedList(token,"en",10)

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

    override fun onSuccess(shipmentAssigned: ShipmentAssigned?, code: Int) {
        shipmentAssignedList = shipmentAssigned!!.data.data
        binding.shimmer.visibility = View.GONE
        binding.rvNewParcel.visibility = View.VISIBLE
        binding.rvNewParcel.adapter = AdapterParcel(this,
            shipmentAssignedList, AdapterParcel.ORIENTATION.HORIZONTAL)
        binding.rvNewParcel.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

    }

    override fun onError(error: String?, code: Int) {
       Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}