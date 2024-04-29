package com.example.newsfeeder.presentation.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.domain.usecase.FeedsUseCase
import com.example.newsfeeder.domain.usecase.GetFeedDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getFeedDetailsUseCase: GetFeedDetailsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val feedsUseCase: FeedsUseCase
) : ViewModel() {

    private val _itemId = MutableStateFlow(savedStateHandle[ITEMID] ?: 0)
    private val itemId: StateFlow<Int> get() = _itemId

    private val _feedDetails: MutableStateFlow<FeedModel> =
        MutableStateFlow(FeedModel(0, "", "", "", "", "", "", FeedSource.HABR, false))
    val feedDetails: MutableStateFlow<FeedModel> get() = _feedDetails

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getFeedDetailsUseCase.getFeedDetails(itemId.value)
            _feedDetails.update { result }
        }
    }

    private fun addToFavourite(itemId: Int) {
        viewModelScope.launch {
            feedsUseCase.addFeedToFavouriteUseCase.addFeedToFavourites(itemId)
        }
    }

    private fun removeFromFavourite(itemId: Int) {
        viewModelScope.launch {
            feedsUseCase.removeFeedFromFavourites.removeFeedToFavourites(itemId)
        }
    }

    fun toggleBookmark(feedModel: FeedModel) {
        val updatedFeed = feedModel.copy(isBookmarked = !feedModel.isBookmarked)
        if (updatedFeed.isBookmarked) {
            addToFavourite(updatedFeed.id)
            _feedDetails.update { updatedFeed }
        } else {
            removeFromFavourite(updatedFeed.id)
            _feedDetails.update { updatedFeed }
        }
    }

    companion object {
        const val ITEMID = "itemId"
    }
}