package com.app.i_express_rider.Model.api


import com.app.i_express_rider.Model.models.GetVerifyToken
import com.app.i_express_rider.Model.models.Login
import com.app.i_express_rider.Model.models.Registration
import com.app.i_express_rider.Model.models.SendOTP
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST


interface ApiInterface {

    @POST("authentication/riders/verify")
    fun sendOtp(
        @HeaderMap headers: Map<String?, String?>?,
        @Body jsonObject:JsonObject
    ): Call<SendOTP?>?

    @POST("authentication/riders/otp/verify")
    fun verifyToken(
        @HeaderMap headers: Map<String?, String?>?,
        @Body jsonObject:JsonObject
    ): Call<GetVerifyToken?>?

    @POST("authentication/riders/registration")
    fun registration(
        @HeaderMap headers: Map<String?, String?>?,
        @Body jsonObject:JsonObject
    ): Call<Registration?>?

    @POST("authentication/riders/login")
    fun login(
        @HeaderMap headers: Map<String?, String?>?,
        @Body jsonObject:JsonObject
    ): Call<Login?>?
}