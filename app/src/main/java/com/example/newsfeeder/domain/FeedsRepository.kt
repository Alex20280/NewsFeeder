package com.example.newsfeeder.domain

import androidx.paging.Pager
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.domain.model.FeedModel

interface FeedsRepository {

    suspend fun insertFeeds()

    fun getLocalFavouriteFeeds():  Pager<Int, FeedsEntity>

    suspend fun getFeedById(feedId: Int): FeedModel

    suspend fun addFeedToFavourite(feedId: Int)

    suspend fun removeFeedFromFavourite(feedId: Int)

    suspend fun getFeedsBySource(feedSources: List<FeedSource>): Pager<Int, FeedsEntity>

}