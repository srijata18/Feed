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
import com.example.feed.database.FeedDatabase
import com.example.feed.database.FeedTableDB
import com.google.gson.Gson


class MainActivity : BaseActivity(),IFeedContract.View , ItemSelected {

    private var presenter : IFeedContract.Presenter ?= null
    private val ACT_REQ_CODE = 101
    var adapter : FeedAdapter ?= null
    private var position = 0

    private var mDb: FeedDatabase? = null
    val tableDB = FeedTableDB()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.view = cl_mainlayout
        presenter = FeedPresenter(this)
        adapter = FeedAdapter(this,this)
        presenter?.initData()
    }

    override fun retrieveSavedData(){
        val data = fetchDataFromDB()//sharedPref?.getString(Constants.FEED_MODEL,"")
        var feedData : Array<FeedDetailsModel> ?= null
        if (data?.isNotEmpty() == true){
            for (value in data) {
                value.feedModel?.let {
                    feedData = it
                }
            }
            feedData?.let{fdata -> data.let{ presenter?.setArrList(fdata) }}
            setAdapter()
        }
    }

    private fun insertIntoDatabase() {
        mDb = FeedDatabase.invoke(this)
        mDb?.getDao()?.clearTable()
        mDb?.getDao()?.insertData(tableDB)
    }

    private fun fetchDataFromDB(): Array<FeedTableDB>? {
        mDb = FeedDatabase.invoke(this)
        val list = mDb?.getDao()?.getDataFromDB()
        return list
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

    override fun setAdapter() {
        val mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_feedDetails.layoutManager = mLinearLayoutManager
        presenter?.getArrlist()?.let { adapter?.updateItem(it) }
        rv_feedDetails.adapter = adapter
        adapter?.notifyDataSetChanged()
        rv_feedDetails.visible()
    }

    override fun setAdapterHeaderValue(date: Long, value: Int) {
        adapter?.addSectionHeaderItem(date, value)
    }

    override fun storeToInternalStorage(arrFeedDetailsModel: Array<FeedDetailsModel>){
        tableDB.feedModel = arrFeedDetailsModel
        insertIntoDatabase()
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
                presenter?.updateList(position,receivedData?.isLiked)
                adapter?.notifyItemChanged(position)
            }
        }
    }
    override fun showNoInternet() {
        Utils.displayNoInternetSnackBar(cl_mainlayout, this)
    }
}
