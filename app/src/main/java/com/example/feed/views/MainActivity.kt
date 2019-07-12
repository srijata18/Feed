package com.example.feed.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import com.example.feed.contract.IFeedContract
import com.example.feed.presenter.FeedPresenter
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.LinearLayoutManager
import com.example.feed.constants.Constants
import com.example.feed.contract.ItemSelected
import com.example.feed.datamodel.FeedDetailsModel
import com.example.feed.utils.Utils
import com.example.feed.R
import com.google.gson.Gson


class MainActivity : BaseActivity(),IFeedContract.View , ItemSelected {

    private var presenter : IFeedContract.Presenter ?= null
    private var headerDates = HashSet<Long>()
    private val ACT_REQ_CODE = 101
    private var arrListFeedDetails = arrayListOf<FeedDetailsModel>()
    var adapter : FeedAdapter ?= null
    private var position = 0
    private var PRIVATE_MODE = 0
    var sharedPref: SharedPreferences ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.view = cl_mainlayout
        presenter = FeedPresenter(this)
        adapter = FeedAdapter(this,this)
        sharedPref = getSharedPreferences(Constants.FEED_MODEL, PRIVATE_MODE)
        presenter?.initData()
    }

    override fun retrieveSavedData(){
        val data = sharedPref?.getString(Constants.FEED_MODEL,"")
        if (!data.isNullOrEmpty()){
            setAdapter(Gson().fromJson(data , Array<FeedDetailsModel>::class.java))
        }
    }


    override fun showProgress(){
        progressDialog?.visible()
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun hideProgress(){
        progressDialog?.gone()
    }

    override fun setAdapter(arrFeedDetailsModel: Array<FeedDetailsModel>) {
        val mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_feedDetails.layoutManager = mLinearLayoutManager
        var index = 0
        for (i in 0 until arrFeedDetailsModel.size) {
            if (!headerDates.contains(arrFeedDetailsModel[i].time)) {
                arrFeedDetailsModel[i].time?.let {
                    adapter?.addSectionHeaderItem(it, index)
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
        adapter?.updateItem(arrListFeedDetails)
        rv_feedDetails.adapter = adapter
        adapter?.notifyDataSetChanged()
        rv_feedDetails.visible()
        storeToInternalStorage(arrFeedDetailsModel)
    }

    private fun storeToInternalStorage(arrFeedDetailsModel: Array<FeedDetailsModel>){
        val editor = sharedPref?.edit()
        editor?.putString(Constants.FEED_MODEL, Gson().toJson(arrFeedDetailsModel))
        editor?.apply()
    }

    override fun onItemSelected(feedDetailsModel: FeedDetailsModel , position : Int) {
        this.position = position
        val intent = Intent(this , FeedDetailedActivity::class.java)
        intent.putExtra(Constants.FEED_MODEL , feedDetailsModel)
        startActivityForResult(intent, ACT_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACT_REQ_CODE){
            if (resultCode == Activity.RESULT_OK ){
                val receivedData = if (data?.hasExtra(Constants.FEED_MODEL)==true)
                    data.getParcelableExtra(Constants.FEED_MODEL) as FeedDetailsModel
                else null
                arrListFeedDetails[position].isLiked = receivedData?.isLiked
                adapter?.notifyItemChanged(position)
            }
        }
    }
    override fun showNoInternet() {
        Utils.displayNoInternetSnackBar(cl_mainlayout, this)
    }
}
