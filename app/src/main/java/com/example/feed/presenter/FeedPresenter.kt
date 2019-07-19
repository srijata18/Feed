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


    private val service = RetrofitClientInstance.create()//getRetrofitInstance()?.create(GetDetails::class.java)
    var arrListFeedDetails = arrayListOf<FeedDetailsModel>()
    private var headerDates = HashSet<Long>()


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
                if (response.body() != null && response.body() is Array<FeedDetailsModel>) {
                    setData(response.body() as Array<FeedDetailsModel>)
                    view.setAdapter()
                    view.storeToInternalStorage(response.body() as Array<FeedDetailsModel>)
                }
                Log.i("INFO::","Success : ${response.body()}")
                view.hideProgress()
            }

            override fun onFailure(call: Call<Array<FeedDetailsModel>>, t: Throwable) {
                view.hideProgress()
                Log.i("INFO::","Error : ${t.stackTrace}")
            }
        })
    }

    override fun getArrlist(): ArrayList<FeedDetailsModel> {
        return arrListFeedDetails
    }

    override fun setData(arrFeedDetailsModel: Array<FeedDetailsModel>) {
        var index = 0
        for (i in 0 until arrFeedDetailsModel.size) {
            if (!headerDates.contains(arrFeedDetailsModel[i].time)) {
                arrFeedDetailsModel[i].time?.let {
                    view.setAdapterHeaderValue(it, index)
                    headerDates.add(it)
                }
                arrListFeedDetails.add(index, FeedDetailsModel("","","","",0,""))
                index++
                arrListFeedDetails.add(index, arrFeedDetailsModel[i])
                index++
            } else {
                arrListFeedDetails.add(index, arrFeedDetailsModel[i])
                index++
            }
        }
    }

    override fun setArrList(arrList: Array<FeedDetailsModel>) {
        arrListFeedDetails.apply {
            clear()
            addAll(arrList)
        }
    }

    override fun updateList(position: Int, value: Boolean?) {
        arrListFeedDetails[position].isLiked = value
    }
}