package com.app.i_express_rider.view.ui.delivery

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.OrderDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class OrderDetailsBottomSheet : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var parcelType: PARCEL_TYPE? = null

    enum class PARCEL_TYPE {
        NEW, ACCEPTED
    }

    companion object {
        var TAG = "BottomSheetFragment"
    }

    private var _binding: OrderDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var sheetBehavior: BottomSheetBehavior<*>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet: FrameLayout? =
                dialog.findViewById(R.id.design_bottom_sheet)
            sheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            sheetBehavior.skipCollapsed = true
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCloseBottomSheet.setOnClickListener {
            Log.d(TAG, "onViewCreated: ")
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.btnReturn.setOnClickListener {
            this.dismiss()
            startActivity(Intent(context, FeedbackActivity::class.java))
        }

        binding.btnMakeDelivery.setOnClickListener {
            this.dismiss()
            startActivity(Intent(context, ConfirmDeliveryParcelActivity::class.java))
        }

        // SharedPreferences

        val pref =
           context?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        binding.senderName.setText(pref?.getString("sender_name",""))
        binding.senderAddress.setText(pref?.getString("sender_address",""))
        binding.senderCallAction.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:"+pref?.getString("sender_phone_number",""))
            context?.startActivity(callIntent)
        }
        binding.itemDescription.setText("Description: "+pref?.getString("item_description","")+"\n"
         + "Weight: "+pref?.getInt("item_weight",0)+" kg"+
                "\n"+"Volume: "+pref?.getInt("item_volume",0)+" cm3")
        binding.worth.setText(""+pref!!.getInt("worth",0)+" TK")
        binding.collectionAmount.setText(""+pref?.getInt("collection_amount",0)+" TK")
        binding.receiverName.setText(pref?.getString("receiver_name",""))
        binding.receiverAddress.setText(pref?.getString("receiver_address",""))
        binding.receiverCallAction.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:"+pref?.getString("receiver_phone_number",""))
            context?.startActivity(callIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}