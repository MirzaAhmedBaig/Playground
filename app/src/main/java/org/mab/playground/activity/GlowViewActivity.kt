package org.mab.playground.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_glow_view.*
import org.avantari.odishadmapp.network.OrissaAPI
import org.mab.playground.R
import org.mab.playground.network.models.UserDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GlowViewActivity : AppCompatActivity() {


    private val TAG = GlowViewActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glow_view)


        request.setOnClickListener {
            //            sendUserDetails(UserDetails("Mirza","7276137498"))
            getUserDetails()
        }
    }

    private fun sendDemoRequest() {
        OrissaAPI.networkManager.getHelloWorld().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                Log.d(TAG, "onFailure $call")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "onResponse ${response.body()} ${response.code()}")
            }

        })
    }

    private fun getUserDetails() {
        OrissaAPI.networkManager.getUserDetails().enqueue(object : Callback<UserDetails> {
            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                t.printStackTrace()
                Log.d(TAG, "onFailure $call")
            }

            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                Log.d(TAG, "onResponse ${response.body()} ${response.code()}")
            }

        })
    }

    private fun sendUserDetails(userDetails: UserDetails) {
        OrissaAPI.networkManager.sendUserDetails(userDetails).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                Log.d(TAG, "onFailure $call")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "onResponse ${response.body()} ${response.code()}")
            }

        })
    }


}
