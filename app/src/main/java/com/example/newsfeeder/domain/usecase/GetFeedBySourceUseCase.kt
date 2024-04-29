package com.example.newsfeeder.domain.usecase

import androidx.paging.Pager
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.domain.FeedsRepository
import javax.inject.Inject

class GetFeedBySourceUseCase  @Inject constructor(
    private val repository: FeedsRepository
) {

    suspend fun getFeedsBySource(feedSources: List<FeedSource>): Pager<Int, FeedsEntity> {
        return repository.getFeedsBySource(feedSources)
    }
}