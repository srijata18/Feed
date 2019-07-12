package com.example.feed.views

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.feed.R
import com.example.feed.contract.ItemSelected
import com.example.feed.datamodel.FeedDetailsModel
import com.example.feed.utils.Utils
import kotlinx.android.synthetic.main.row_item_feed.view.*

class FeedAdapter(private val context : Context, val itemSelected : ItemSelected) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_HEADER = 1
    private val sectionHeader = HashMap<Int,Long>()
    var feedDetailsList = arrayListOf<FeedDetailsModel>()

    override fun getItemCount(): Int {
        return feedDetailsList.count()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {

            if (!feedDetailsList[position].text.isNullOrEmpty() && !feedDetailsList[position].imageUrl.isNullOrEmpty()) {
                holder.iv_feed_details.gone()
                holder.tv_feed_details.gone()
                holder.tv_feed.apply {
                    this.visible()
                    text = feedDetailsList[position].text
                }
                holder.iv_feed.visible()
                Glide.with(context)
                    .load(feedDetailsList[position].imageUrl)
                    .asBitmap()
                    .into(holder.iv_feed)
            }else if (!feedDetailsList[position].text.isNullOrEmpty()){
                holder.tv_feed.gone()
                holder.iv_feed.gone()
                holder.iv_feed_details.gone()
                holder.tv_feed_details.apply {
                    this.visible()
                    text = feedDetailsList[position].text
                }
            }else if(!feedDetailsList[position].imageUrl.isNullOrEmpty()){
                holder.tv_feed.gone()
                holder.iv_feed.gone()
                holder.tv_feed_details.gone()
                holder.iv_feed_details.visible()
                Glide.with(context)
                    .load(feedDetailsList[position].imageUrl)
                    .asBitmap()
                    .into(holder.iv_feed_details)
            }

            //Feed Header
            holder.feed_header.apply {
                this.visible()
                text = feedDetailsList[position].title
            }
            holder.tv_details.apply {
                this.visible()
                text = "${context.getString(R.string.from)} ${feedDetailsList[position].name}"
            }

            //handle click event for row item
            holder.cl_row_itwm_feed.setOnClickListener {
                itemSelected.onItemSelected(feedDetailsList[position],position)
            }
            var isLiked = if (feedDetailsList[position].isLiked == false){
                Utils.enableUnlikeView(holder.btn_like , context)
                false
            }else{
                Utils.enableLikeView(holder.btn_like , context)
                true
            }
            holder.btn_like?.setOnClickListener { if (isLiked){
                isLiked = false
                feedDetailsList[position].isLiked = isLiked
                Utils.enableUnlikeView(holder.btn_like , context)
                }else{
                isLiked = true
                feedDetailsList[position].isLiked = isLiked
                Utils.enableLikeView(holder.btn_like , context)
            }
            }
        }else if ((holder is HeaderViewHolder)){
            holder.tv_sectionHeader.text = sectionHeader[position].toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.row_item_feed, parent, false)
            ViewHolder(v)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_section_header, parent, false)
            HeaderViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (sectionHeader.contains(position)) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    fun addSectionHeaderItem(item: Long, position: Int) {
        if (!sectionHeader.containsKey(position)) {
            sectionHeader[position] = item
        }
    }

    fun updateItem(itemList : ArrayList<FeedDetailsModel>){
        feedDetailsList.addAll(itemList)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tv_feed = view.tv_feed
        val iv_feed = view.iv_feed
        val feed_header = view.feed_header
        val tv_feed_details = view.tv_feed_details
        val iv_feed_details = view.iv_feed_details
        val btn_like = view.btn_like
        val tv_details = view.tv_details
        val cl_row_itwm_feed = view.cl_row_item_feed
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_sectionHeader = view.findViewById<TextView>(R.id.tv_sectionHeader)
    }
}