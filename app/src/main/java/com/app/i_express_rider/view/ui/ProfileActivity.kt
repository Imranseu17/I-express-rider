package com.app.i_express_rider.view.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.app.i_express_rider.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val itemsSearchBy = listOf("Location", "Area")
        binding.menuSearchBy.editText?.setText(itemsSearchBy[0])
        val adapterSearchBy =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, itemsSearchBy)
        (binding.menuSearchBy.editText as? AutoCompleteTextView)?.setAdapter(adapterSearchBy)

        val itemsParcelType = listOf("All", "Normal", "Express")
        binding.menuParcelType.editText?.setText(itemsParcelType[0])
        val adapterParcelType =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, itemsParcelType)
        (binding.menuParcelType.editText as? AutoCompleteTextView)?.setAdapter(adapterParcelType)
        (binding.menuParcelType.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->

        }

        val itemsOrderWith = listOf("Collective", "Non-collective", "All")
        binding.menuOrderWith.editText?.setText(itemsOrderWith[0])
        val adapterOrderWith =
            ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, itemsOrderWith)
        (binding.menuOrderWith.editText as? AutoCompleteTextView)?.setAdapter(adapterOrderWith)


        binding.fabEditPickUpArea.setOnClickListener {
            PickAreaFragment().show(supportFragmentManager, "PickAreaFragment")
        }

        binding.fabEditDropOffArea.setOnClickListener {
            PickAreaFragment().show(supportFragmentManager, "PickAreaFragment")
        }
    }
}