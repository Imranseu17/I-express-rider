package com.app.i_express_rider.Model.models

import com.google.gson.annotations.SerializedName

data class Registration (
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : String,
    @SerializedName("code") val code : Int,
    @SerializedName("data") val data : String
        )
