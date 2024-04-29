package com.example.newsfeeder.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.example.newsfeeder.data.local.AppDatabase
import com.example.newsfeeder.data.local.NewsFeedDao
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.data.network.NextWebFeedApiService
import com.example.newsfeeder.data.network.TechCrunchApiService
import com.example.newsfeeder.data.network.HabrFeedApiService
import com.example.newsfeeder.domain.FeedsRepository
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.domain.service.FeedSourceAllocatorService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedsRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val feedDao: NewsFeedDao,
    private val nextWebFeedApiService: NextWebFeedApiService,
    private val techCrunchApiService: TechCrunchApiService,
    private val habrFeedApiService: HabrFeedApiService,
    private val feedSourceAllocatorService: FeedSourceAllocatorService
) : FeedsRepository {

    override suspend fun insertFeeds() {
        val habrResponse = withContext(Dispatchers.IO) {
            habrFeedApiService.fetchHabrFeeds(20).execute()
        }

        val nextWebResponse = withContext(Dispatchers.IO) {
            nextWebFeedApiService.fetchNextWebFeeds().execute()
        }

        val techCrunchResponse = withContext(Dispatchers.IO) {
            techCrunchApiService.fetchTechCrunchFeeds().execute()
        }

        if (habrResponse.isSuccessful && nextWebResponse.isSuccessful && techCrunchResponse.isSuccessful) {
            // Use a transaction to ensure atomicity
            database.withTransaction {
                // Clear the table only for refresh, not for prepend or append
                habrResponse.body()?.channel?.items?.map {
                    if(!database.newsFeedsDao().getFeedByPubDate(it.pubDate)){
                            database.newsFeedsDao().insertFeed(it.toFeedEntity(feedSourceAllocatorService))
                        }
                }

                nextWebResponse.body()?.channel?.items?.map {
                    if (!database.newsFeedsDao().getFeedByPubDate(it.pubDate)) {
                        database.newsFeedsDao()
                            .insertFeed(it.toFeedEntity(feedSourceAllocatorService))
                    }
                }

                techCrunchResponse.body()?.channel?.items?.map {
                    if (!database.newsFeedsDao().getFeedByPubDate(it.pubDate)) {
                        database.newsFeedsDao()
                            .insertFeed(it.toFeedEntity(feedSourceAllocatorService))
                    }
                }
            }
        }
    }

    override fun getLocalFavouriteFeeds():  Pager<Int, FeedsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                feedDao.getFavouriteFeeds()
            }
        )
    }

    override suspend fun getFeedById(feedId: Int): FeedModel {
        return feedDao.getFeedById(feedId).toModel()
    }

    override suspend fun addFeedToFavourite(feedId: Int) {
        return feedDao.addFeedToFavourite(feedId)
    }

    override suspend fun removeFeedFromFavourite(feedId: Int) {
        return feedDao.removeFeedToFavourite(feedId)
    }

    override suspend fun getFeedsBySource(feedSources: List<FeedSource>): Pager<Int, FeedsEntity> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                feedDao.getFeedsBySources(feedSources)
            }
        )
    }
}