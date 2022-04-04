package com.app.i_express_rider.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.i_express_rider.databinding.FragmentHistoryBinding
import com.app.i_express_rider.viewmodel.DataViewModel
import java.util.*

class HistoryFragment : Fragment() {

    private val TAG: String = "HistoryFragment"

    private lateinit var dataViewModel: DataViewModel
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataViewModel =
            ViewModelProvider(this).get(DataViewModel::class.java)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnHistory.setOnClickListener {
            startActivity(Intent(context, HistoryActivity::class.java))
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}