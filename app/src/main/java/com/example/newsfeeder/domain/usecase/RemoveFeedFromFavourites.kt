package com.example.newsfeeder.domain.usecase

import com.example.newsfeeder.domain.FeedsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoveFeedFromFavourites @Inject constructor(
    private val repository: FeedsRepository
) {
    suspend fun removeFeedToFavourites(feedId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFeedFromFavourite(feedId)
        }
    }
}