package com.app.i_express_rider.Model.api


import com.app.i_express_rider.Model.models.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap


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

    @GET("rider/shipping/shipments/assigned")
    fun shipmentAssignedList(
        @HeaderMap headers: Map<String?, String?>?,
        @Query("lang")lang:String , @Query("limit")limit:Int
    ): Call<ShipmentAssigned?>?
}