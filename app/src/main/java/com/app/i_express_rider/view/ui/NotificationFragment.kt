package com.app.i_express_rider.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.i_express_rider.databinding.FragmentNotificationBinding
import com.app.i_express_rider.view.adapter.AdapterNotification
import com.app.i_express_rider.viewmodel.DataViewModel

class NotificationFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel
    private var _binding: FragmentNotificationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataViewModel =
            ViewModelProvider(this).get(DataViewModel::class.java)

        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.rvNotification.adapter = AdapterNotification(context)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}