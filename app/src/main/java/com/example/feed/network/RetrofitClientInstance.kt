package com.example.feed.network

import com.example.feed.constants.Constants
import com.example.feed.datamodel.FeedDetailsModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitClientInstance {

    @GET("bins/bpnll")
    fun getAllFeeds(): Call<Array<FeedDetailsModel>>

    companion object RetroClient {
        fun create() : RetrofitClientInstance {
            val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RetrofitClientInstance::class.java)
        }
    }
}