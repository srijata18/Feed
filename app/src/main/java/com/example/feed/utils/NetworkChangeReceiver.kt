package com.example.feed.utils


import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import android.view.View


class NetworkChangeReceiver : BroadcastReceiver() {

    var view : View ?= null

    override fun onReceive(context: Context, intent: Intent) {

        val status = NetworkUtil.getConnectivityStatus(context)
        Log.e("network Receiver", "Network change received")
        if ("android.net.conn.CONNECTIVITY_CHANGE" == intent.action) {
            if (status == NetworkUtil.DISCONNECTED) {
                Utils.displayNoInternetSnackBar(view,context)
            } else {
                Utils.hideNoInternetSnackBar()
            }
        }
    }
}