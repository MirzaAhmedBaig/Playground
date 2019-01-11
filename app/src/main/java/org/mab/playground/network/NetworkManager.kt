package org.mab.playground.network

import org.mab.playground.network.models.UserDetails
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkManager {

    @GET("gethello")
    fun getHelloWorld(): Call<String>

    @GET("getuserdetails")
    fun getUserDetails(): Call<UserDetails>

    @POST("senduserdetails")
    fun sendUserDetails(@Body userDetails: UserDetails): Call<String>
}
