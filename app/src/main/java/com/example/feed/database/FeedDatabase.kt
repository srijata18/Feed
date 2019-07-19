package com.example.feed.database

import android.arch.persistence.room.*
import android.content.Context
import com.example.feed.constants.DBConstants

@Database(entities = [FeedTableDB::class], version = DBConstants.DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class FeedDatabase : RoomDatabase() {

    abstract fun getDao(): FeedDao

    companion object {
        @Volatile private var instance: FeedDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            FeedDatabase::class.java, DBConstants.DB_NAME)
            .allowMainThreadQueries()
            .build()
    }

}