package com.app.i_express_rider.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {

    companion object {
        val selected = MutableLiveData<Boolean>().apply {
            value = false
        }
    }
}