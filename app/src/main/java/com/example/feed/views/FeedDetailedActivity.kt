package com.example.feed.views

import android.app.Activity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.feed.constants.Constants
import com.example.feed.R
import com.example.feed.datamodel.FeedDetailsModel
import com.example.feed.utils.Utils
import kotlinx.android.synthetic.main.layout_feed_details.*
import kotlinx.android.synthetic.main.row_item_feed.view.*

class FeedDetailedActivity : BaseActivity() {

    var recievedDetails : FeedDetailsModel ?= null
    var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_feed_details)
        super.view = tv_description
        if (intent.hasExtra(Constants.FEED_MODEL)) {
            recievedDetails = intent.getParcelableExtra(Constants.FEED_MODEL) as FeedDetailsModel
        }
        setValue()
    }

    override fun onBackPressed() {
        if (isLiked != recievedDetails?.isLiked){
            recievedDetails?.isLiked = isLiked
            intent.putExtra(Constants.FEED_MODEL, recievedDetails)
            setResult(Activity.RESULT_OK, intent)
        }
        finish()
    }
    fun setValue() {
        tv_sectionHeader?.text = recievedDetails?.time?.let { Utils.convertEpochTimeToDateTime(it.toString())}
        tv_descriptionDetails?.text = recievedDetails?.description ?: ""
        if (!recievedDetails?.text.isNullOrEmpty() && !recievedDetails?.imageUrl.isNullOrEmpty()) {
            layout_content?.apply {
                iv_feed_details.gone()
                tv_feed_details.gone()
                tv_feed.apply {
                    this.visible()
                    text = recievedDetails?.text ?: ""
                }
                iv_feed.visible()
                Glide.with(context)
                    .load(recievedDetails?.imageUrl)
                    .asBitmap()
                    .into(this.iv_feed)
            }
        } else if (!recievedDetails?.text.isNullOrEmpty()) {
            layout_content?.apply {
                tv_feed.gone()
                iv_feed.gone()
                iv_feed_details.gone()
                tv_feed_details.apply {
                    this.visible()
                    text = recievedDetails?.text ?: ""
                }
            }
        } else if (!recievedDetails?.imageUrl.isNullOrEmpty()) {
            layout_content?.apply {
                tv_feed.gone()
                iv_feed.gone()
                tv_feed_details.gone()
                iv_feed_details.visible()
                Glide.with(context)
                    .load(recievedDetails?.imageUrl)
                    .asBitmap()
                    .into(this.iv_feed_details)
            }
        }
        layout_content?.tv_details.apply {
            this?.visible()
            this?.text = "${this@FeedDetailedActivity.getString(R.string.from)} ${recievedDetails?.name ?: ""}"
        }
        isLiked = if (recievedDetails?.isLiked == false) {
            Utils.enableUnlikeView(layout_content.btn_like , this)
            false
        } else {
            Utils.enableLikeView(layout_content.btn_like , this)
            true
        }
        layout_content.btn_like?.setOnClickListener {
            if (isLiked) {
                isLiked = false
                Utils.enableUnlikeView(layout_content.btn_like , this)
            } else {
                isLiked = true
                Utils.enableLikeView(layout_content.btn_like , this)
            }
        }
    }
}