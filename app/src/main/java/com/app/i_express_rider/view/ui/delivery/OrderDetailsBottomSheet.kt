package com.app.i_express_rider.view.ui.delivery

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}