package com.example.newsfeeder.presentation.ui.bottomnav.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.domain.usecase.FeedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedNewsScreenViewModel @Inject constructor(
    private val feedsUseCase: FeedsUseCase
): ViewModel() {

    private var _favouriteFeedsFlow: Flow<PagingData<FeedModel>> =
        flowOf<PagingData<FeedModel>>().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            PagingData.empty()
        )
    val favouriteFeedsFlow: Flow<PagingData<FeedModel>> get() = _favouriteFeedsFlow

    fun getFavouriteFeeds() {
        viewModelScope.launch {
            _favouriteFeedsFlow = feedsUseCase.getLocalFavouriteFeedsUseCase.getFavouriteFeeds().flow
                .map { feedList ->
                    feedList.map { it.toModel()}
                }
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        }
    }

    fun addToFavourite(itemId: Int) {
        viewModelScope.launch {
            feedsUseCase.addFeedToFavouriteUseCase.addFeedToFavourites(itemId)
        }
    }

    fun removeFromFavourite(itemId: Int) {
        viewModelScope.launch {
            feedsUseCase.removeFeedFromFavourites.removeFeedToFavourites(itemId)
        }
    }

}