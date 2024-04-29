package com.example.newsfeeder.domain.usecase

import com.example.newsfeeder.domain.FeedsRepository
import com.example.newsfeeder.domain.model.FeedModel
import javax.inject.Inject

class GetFeedDetailsUseCase @Inject constructor(
    private val repository: FeedsRepository
) {

    suspend fun getFeedDetails(feedId: Int): FeedModel {
        return repository.getFeedById(feedId)
    }
}