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
}