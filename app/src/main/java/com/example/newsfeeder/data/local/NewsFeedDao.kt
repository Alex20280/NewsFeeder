package com.example.newsfeeder.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.data.local.entities.FeedsEntity

@Dao
interface NewsFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeed(feed: FeedsEntity)

    @Query("SELECT * FROM FeedsEntity WHERE isBookmarked = 1")
    fun getFavouriteFeeds(): PagingSource<Int, FeedsEntity>

    @Query("SELECT * FROM FeedsEntity WHERE id = :feedId")
    fun getFeedById(feedId: Int): FeedsEntity

    @Query("UPDATE FeedsEntity SET isBookmarked = 0 WHERE id = :feedId")
    suspend fun removeFeedToFavourite(feedId: Int)

    @Query("UPDATE FeedsEntity SET isBookmarked = 1 WHERE id = :feedId")
    suspend fun addFeedToFavourite(feedId: Int)

    @Query("SELECT * FROM FeedsEntity WHERE pubDate = :pubDate")
    fun getFeedByPubDate(pubDate: String?): Boolean

    @Query("DELETE FROM FeedsEntity ")
    suspend fun deleteAll()

    @Query("SELECT * FROM FeedsEntity WHERE source IN (:feedSources) ORDER BY pubDateLong DESC")
    fun getFeedsBySources(feedSources: List<FeedSource>): PagingSource<Int, FeedsEntity>

}

