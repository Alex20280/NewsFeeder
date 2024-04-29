package com.example.newsfeeder.data.local.converter

import androidx.room.TypeConverter
import com.example.newsfeeder.data.local.entities.FeedSource

class FeedSourceConverter {
    @TypeConverter
    fun fromFeedSource(value: String): FeedSource {
        return enumValueOf(value)
    }

    @TypeConverter
    fun toFeedSource(value: FeedSource): String {
        return value.name
    }
}