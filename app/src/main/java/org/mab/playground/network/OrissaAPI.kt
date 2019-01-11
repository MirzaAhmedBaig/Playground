package org.avantari.odishadmapp.network

import com.google.gson.GsonBuilder
import org.mab.playground.network.NetworkManager
import org.mab.playground.network.Oconfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OrissaAPI {
    companion object {
        private val gson by lazy {
            GsonBuilder()
                    .setLenient()
                    .create()
        }

        private val retrofit by lazy {
            Retrofit.Builder()
                    .baseUrl(Oconfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }

        val networkManager by lazy {
            retrofit.create(NetworkManager::class.java)
        }

    }
}