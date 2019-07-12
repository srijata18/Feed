package com.example.feed.views

import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.example.feed.R
import com.example.feed.utils.NetworkChangeReceiver
import kotlinx.android.synthetic.main.activity_main.*

open class BaseActivity : AppCompatActivity(){

    private val networkChangeReceiver = NetworkChangeReceiver()
    var view : View?= null

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver.view = view
        registerReceiver(networkChangeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }
}