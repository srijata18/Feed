package com.example.feed.contract

import com.example.feed.datamodel.FeedDetailsModel

interface IFeedContract {
    interface View{
        fun showProgress()
        fun isNetworkAvailable(): Boolean
        fun hideProgress()
        fun showNoInternet()
        fun setAdapter(arrFeedDetailsModel: Array<FeedDetailsModel>)
        fun retrieveSavedData()
    }
    interface Presenter{
        fun initData()
    }
}