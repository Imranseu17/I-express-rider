package com.app.i_express_rider.Model.models

import com.google.gson.annotations.SerializedName

data class DataVerifyToken(
    @SerializedName("verifyToken") val verifyToken : String
)
