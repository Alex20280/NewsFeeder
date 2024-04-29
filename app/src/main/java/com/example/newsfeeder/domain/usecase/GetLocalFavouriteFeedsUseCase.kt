package com.example.newsfeeder.domain.usecase

import androidx.paging.Pager
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.domain.FeedsRepository
import javax.inject.Inject

class GetLocalFavouriteFeedsUseCase @Inject constructor(
    private val repository: FeedsRepository
) {

    fun getFavouriteFeeds(): Pager<Int, FeedsEntity> {
        return repository.getLocalFavouriteFeeds()
    }
}

