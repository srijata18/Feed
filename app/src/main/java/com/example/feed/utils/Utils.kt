package com.example.feed.utils

import android.content.Context
import android.view.View
import android.widget.Button
import com.example.feed.R
import com.example.feed.customViews.CustomSnackView

class Utils {
    companion object {

        var noInternetConnectionSnackbar: CustomSnackView? = null

        fun hideNoInternetSnackBar() {
            noInternetConnectionSnackbar?.let{
                it.snackbar?.dismiss()
                noInternetConnectionSnackbar = null
            }
        }

        fun displayNoInternetSnackBar(view: View?, context: Context): CustomSnackView {
            noInternetConnectionSnackbar = CustomSnackView(view!!, context)
            noInternetConnectionSnackbar!!.createSnackBar()
            noInternetConnectionSnackbar!!.displaySnackBar()
            noInternetConnectionSnackbar!!.dismissedClickedListner(object : CustomSnackView.DismissClicked {
                override fun onDismissedClicked() {
                    noInternetConnectionSnackbar = null
                }
            }
            )
            return noInternetConnectionSnackbar!!
        }
        fun enableLikeView(view: Button?, context : Context){
            view?.apply {
                setBackgroundResource(R.drawable.rounded_corner_white)
                setTextColor(context.resources.getColor(R.color.colorAccent))
                text = context.getString(R.string.like)
            }
        }

        fun enableUnlikeView(view : Button?, context : Context){
            view?.apply {
                setBackgroundResource(R.drawable.rounded_corner_blue)
                setTextColor(context.resources.getColor(android.R.color.white))
                text = context.getString(R.string.unlike)
            }
        }
    }
}