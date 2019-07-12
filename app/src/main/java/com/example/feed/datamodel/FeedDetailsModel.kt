package com.example.feed.datamodel

import android.os.Parcel
import android.os.Parcelable

data class FeedDetailsModel(
    val description: String? = "",
    val imageUrl: String? = "",
    val name: String? = "",
    val text: String? = "",
    val time: Long? = 0,
    val title: String? = "",
    var isLiked: Boolean? = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(imageUrl)
        parcel.writeString(name)
        parcel.writeString(text)
        parcel.writeValue(time)
        parcel.writeString(title)
        parcel.writeValue(isLiked)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeedDetailsModel> {
        override fun createFromParcel(parcel: Parcel): FeedDetailsModel {
            return FeedDetailsModel(parcel)
        }

        override fun newArray(size: Int): Array<FeedDetailsModel?> {
            return arrayOfNulls(size)
        }
    }
}