package com.example.newsfeeder.domain.usecase

import com.example.newsfeeder.domain.FeedsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddFeedToFavouriteUseCase @Inject constructor(
    private val repository: FeedsRepository
) {

    suspend fun addFeedToFavourites(feedId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addFeedToFavourite(feedId)
        }
    }
}