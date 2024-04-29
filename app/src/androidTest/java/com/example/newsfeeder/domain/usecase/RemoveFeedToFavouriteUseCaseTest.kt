package com.example.newsfeeder.domain.usecase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsfeeder.data.local.AppDatabase
import com.example.newsfeeder.data.local.NewsFeedDao
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.domain.model.FeedModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class RemoveFeedToFavouriteUseCaseTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: NewsFeedDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.newsFeedsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testAddFeedToFavourites() = runBlocking {
        // Given
        val feedId = 1
        val initialFeed = FeedsEntity(
            id = 1,
            image = "image",
            pubDate = "pubDate",
            pubDateLong  = 1000L,
            title = "title",
            author = "author",
            content = "content",
            link = "link",
            source = FeedSource.HABR,
            isBookmarked = false
        )

        val expectedFeed = FeedModel(
            id = 1,
            image = "image",
            pubDate = "pubDate",
            title = "title",
            author = "author",
            content = "content",
            link = "link",
            source = FeedSource.HABR,
            isBookmarked = false
        )

        dao.insertFeed(initialFeed)
        dao.addFeedToFavourite(feedId)
        dao.removeFeedToFavourite(feedId)
        val result = dao.getFeedById(feedId)
        TestCase.assertEquals(expectedFeed, result.toModel())
    }
}