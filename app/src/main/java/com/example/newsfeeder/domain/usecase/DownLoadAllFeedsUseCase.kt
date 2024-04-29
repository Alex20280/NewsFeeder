package com.example.newsfeeder.domain.usecase

import com.example.newsfeeder.domain.FeedsRepository
import javax.inject.Inject

class DownLoadAllFeedsUseCase @Inject constructor(
    private val repository: FeedsRepository
) {
    suspend fun downloadAllFeeds() {
        return repository.insertFeeds()
    }
}
