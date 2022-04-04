package com.app.i_express_rider.Model.api

import com.app.i_express_rider.Model.models.Login
import retrofit2.Call
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("/login")
    fun login(
        @HeaderMap headers: Map<String?, String?>?,
        @Query("number") phoneNumber: String?,
        @Query("password") password: String?
    ): Call<Login?>?
}