package com.example.feed.network

import com.example.feed.datamodel.FeedDetailsModel
import retrofit2.Call
import retrofit2.http.GET

interface GetDetails {
    @GET("bins/bpnll")
    fun getAllFeeds(): Call<Array<FeedDetailsModel>>

}