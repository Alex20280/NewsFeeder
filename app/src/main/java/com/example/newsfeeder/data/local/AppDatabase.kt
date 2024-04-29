package com.example.newsfeeder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsfeeder.data.local.AppDatabase.Companion.VERSION_DATABASE
import com.example.newsfeeder.data.local.converter.FeedSourceConverter
import com.example.newsfeeder.data.local.entities.FeedsEntity

@Database(entities = [FeedsEntity::class], version = VERSION_DATABASE)
@TypeConverters(FeedSourceConverter::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun newsFeedsDao(): NewsFeedDao

    companion object {
        const val VERSION_DATABASE = 2
        const val DATABASE_NAME = "verum_db"
    }
}