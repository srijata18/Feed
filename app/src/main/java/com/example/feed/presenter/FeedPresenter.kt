package com.example.feed.presenter

import android.util.Log
import com.example.feed.contract.IFeedContract
import com.example.feed.datamodel.FeedDetailsModel
import com.example.feed.network.GetDetails
import com.example.feed.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedPresenter(val view : IFeedContract.View) : IFeedContract.Presenter{


    private val service = RetrofitClientInstance().getRetrofitInstance()?.create(GetDetails::class.java)

    override fun initData() {
        if(view.isNetworkAvailable()){
            view.showProgress()
            getDetails()
        }else{
            view.apply {
                hideProgress()
                showNoInternet()
                retrieveSavedData()
            }
        }
    }

    private fun getDetails(){
        val call = service?.getAllFeeds()
        call?.enqueue(object : Callback<Array<FeedDetailsModel>> {
            override fun onResponse(call: Call<Array<FeedDetailsModel>>, response: Response<Array<FeedDetailsModel>>) {
                if (response.body() != null && response.body() is Array<FeedDetailsModel>)
                    view.setAdapter(response.body() as Array<FeedDetailsModel>)
                Log.i("INFO::","Success : ${response.body()}")
                view.hideProgress()
            }

            override fun onFailure(call: Call<Array<FeedDetailsModel>>, t: Throwable) {
                view.hideProgress()
                Log.i("INFO::","Error : ${t.stackTrace}")
            }
        })
    }
}