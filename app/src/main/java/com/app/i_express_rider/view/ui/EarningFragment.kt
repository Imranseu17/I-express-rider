package com.app.i_express_rider.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.i_express_rider.databinding.FragmentEarningBinding
import com.app.i_express_rider.viewmodel.DataViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

class EarningFragment : Fragment() {

    private lateinit var dataViewModel: DataViewModel
    private var _binding: FragmentEarningBinding? = null

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

        _binding = FragmentEarningBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabProfileSetting.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }

        binding.aaChartView.aa_drawChartWithChartModel(createDataChart())

    }

    private fun createDataChart(): AAChartModel {
        return AAChartModel()
            .title("Report graph")
            .chartType(AAChartType.Areaspline)
            .dataLabelsEnabled(true)
            .yAxisTitle("delivery number")
            .xAxisTickInterval(1)
            .series(arrayOf(
                AASeriesElement()
                    .name("Today")
                    .color("#ff6659")
                    .data(arrayOf(7, 6, 9, 14, 5, 21, 25)),
            ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}