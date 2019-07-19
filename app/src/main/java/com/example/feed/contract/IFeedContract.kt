package com.example.feed.contract

import com.example.feed.datamodel.FeedDetailsModel

interface IFeedContract {
    interface View{
        fun showProgress()
        fun isNetworkAvailable(): Boolean
        fun hideProgress()
        fun showNoInternet()
        fun setAdapter()
        fun retrieveSavedData()
        fun storeToInternalStorage(arrFeedDetailsModel: Array<FeedDetailsModel>)
        fun setAdapterHeaderValue( date : Long, value : Int)
    }
    interface Presenter{
        fun initData()
        fun setData(arrFeedDetailsModel: Array<FeedDetailsModel>)
        fun getArrlist() :  ArrayList<FeedDetailsModel>
        fun setArrList(arrList : Array<FeedDetailsModel>)
        fun updateList(position : Int, value : Boolean?)
    }
}