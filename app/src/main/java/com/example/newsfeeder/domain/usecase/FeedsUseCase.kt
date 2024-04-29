package com.example.newsfeeder.domain.usecase

import javax.inject.Inject

data class FeedsUseCase @Inject constructor(
    val addFeedToFavouriteUseCase: AddFeedToFavouriteUseCase,
    val downLoadAllFeedsUseCase: DownLoadAllFeedsUseCase,
    val getFeedBySourceUseCase: GetFeedBySourceUseCase,
    val getFeedDetailsUseCase: GetFeedDetailsUseCase,
    val getLocalFavouriteFeedsUseCase: GetLocalFavouriteFeedsUseCase,
    val removeFeedFromFavourites: RemoveFeedFromFavourites,
)