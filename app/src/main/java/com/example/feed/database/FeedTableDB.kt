package com.example.feed.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.feed.constants.DBConstants
import com.example.feed.datamodel.FeedDetailsModel

@Entity(tableName = DBConstants.DB_TABLE)
data class FeedTableDB (
    @PrimaryKey(autoGenerate = true)
    var itemId : Int? = 0,

    @ColumnInfo(name = DBConstants.MODEL)
    var feedModel : Array<FeedDetailsModel>? = null
)
