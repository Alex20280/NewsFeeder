package com.example.newsfeeder.domain.usecase

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsfeeder.data.local.AppDatabase
import com.example.newsfeeder.data.local.NewsFeedDao
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.data.local.entities.FeedsEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DownLoadAllFeedsUseCaseTest {
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
    fun testInsertFeeds() = runBlocking {
        // Given
        val sourceList = listOf(FeedSource.NEXTWEB)
        val feed = FeedsEntity(
            1,
            "image",
            "date",
            10000L,
            "description",
            "author",
            "content",
            "link",
            FeedSource.NEXTWEB,
            false
        )

        // When
        dao.insertFeed(feed)
        val feeds = dao.getFeedsBySources(sourceList)

        // Then
        assertTrue(feeds is PagingSource<Int, FeedsEntity>)
    }
}
