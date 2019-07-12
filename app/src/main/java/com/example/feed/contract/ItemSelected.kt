package com.example.feed.contract

import com.example.feed.datamodel.FeedDetailsModel

interface ItemSelected {
    fun onItemSelected(feedDetailsModel: FeedDetailsModel , position: Int)
}