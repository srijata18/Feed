package com.example.feed.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.feed.constants.DBConstants

@Dao
interface FeedDao {

    @Insert
    fun insertData(feedTableDB : FeedTableDB): Long

    @Query("SELECT * FROM ${DBConstants.DB_TABLE}")
    fun getDataFromDB(): Array<FeedTableDB>?

    @Query("DELETE FROM ${DBConstants.DB_TABLE}")
    fun clearTable()

}