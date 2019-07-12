package com.example.feed.utils

import android.content.Context
import android.net.ConnectivityManager


object NetworkUtil {
    const val CONNECTED = 1
    const val DISCONNECTED = 0

    fun getConnectivityStatus(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.isConnectedOrConnecting) {
                return CONNECTED
            }
        }
        return DISCONNECTED
    }

//    fun getConnectivityStatusString(context: Context): Int {
//        val conn = NetworkUtil.getConnectivityStatus(context)
//        var status = 0
//        if (conn == NetworkUtil.TYPE_WIFI) {
//            status = NETWORK_STATUS_WIFI
//        } else if (conn == NetworkUtil.TYPE_MOBILE) {
//            status = NETWORK_STATUS_MOBILE
//        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
//            status = NETWORK_STATUS_NOT_CONNECTED
//        }
//        return status
//    }
}