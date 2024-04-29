package com.example.newsfeeder.presentation.ui.bottomnav.feeds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.domain.usecase.FeedsUseCase
import com.example.newsfeeder.presentation.ui.connectivityobserver.ConnectivityObserver
import com.example.newsfeeder.presentation.ui.connectivityobserver.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver,
    private val feedsUseCase: FeedsUseCase
): ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val initialFeedList = listOf(FeedSource.HABR, FeedSource.NEXTWEB, FeedSource.TECHCRUNCH)

    private val _isSnackBarVisible = MutableStateFlow(false)
    val isSnackBarVisible: StateFlow<Boolean> get() = _isSnackBarVisible

    private val _isInternetOn = MutableStateFlow(false)
    val isInternetOn: StateFlow<Boolean> get() = _isInternetOn

    private val _sourceStatus = MutableStateFlow<String>("")
    val sourceStatus: StateFlow<String> get() = _sourceStatus
    fun setSource(source: String) {
        _sourceStatus.value = source
    }

    private var feedsFlow: Flow<PagingData<FeedModel>> = flowOf<PagingData<FeedModel>>()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    fun getFeedsFlow() = feedsFlow

    init {
        viewModelScope.launch {
            val isConnected = networkConnectivityObserver.observe().first() == ConnectivityObserver.Status.AVAILABLE
            if (isConnected) {
                downloadAllFeeds()
            }
        }
        observerNetworkConnectivity()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            if (isInternetOn.value) {
                getFeedsBySource()
            }
            delay(1500)
            _isRefreshing.value = false
        }
    }

    fun getFeedsBySource() {
        viewModelScope.launch {
            val result = feedsUseCase.getFeedBySourceUseCase.getFeedsBySource(parseFeedSources(
                _sourceStatus.value.takeIf {
                    it.isNotEmpty() && it != "{source}" && it != "[]"
                } ?: initialFeedList.joinToString())
            ).flow.map { feedList ->
                feedList.map {
                    it.toModel()
                }
            }
            feedsFlow = result
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        }
    }

    private fun downloadAllFeeds() {
        viewModelScope.launch {
            feedsUseCase.downLoadAllFeedsUseCase.downloadAllFeeds()
        }
    }

    private fun parseFeedSources(input: String): List<FeedSource> {
        val sourceStrings = input
            .replace("[", "")
            .replace("]", "")
            .split(", ")
            .map { it.trim() }
        return sourceStrings.mapNotNull { sourceString ->
            FeedSource.entries.find { it.sourceString.equals(sourceString, ignoreCase = true) }
        }
    }

fun observerNetworkConnectivity() {
    viewModelScope.launch {
        var firstEmission = true
        networkConnectivityObserver.observe()
            .collect { status ->
                if (firstEmission) {
                    firstEmission = false
                    _isInternetOn.value = status == ConnectivityObserver.Status.AVAILABLE
                } else {
                    _isSnackBarVisible.value = true
                    when (status) {
                        ConnectivityObserver.Status.AVAILABLE -> {
                            _isSnackBarVisible.value = true
                            _isInternetOn.value = true
                        }
                        ConnectivityObserver.Status.UNAVAILABLE, ConnectivityObserver.Status.LOST, ConnectivityObserver.Status.NONE -> {
                            _isSnackBarVisible.value = true
                            _isInternetOn.value = false
                        }
                    }
                }
            }
    }
}

    fun dismissSnackBar() {
        _isSnackBarVisible.value = false
        if (isInternetOn.value){
            refreshFeed()
        }else{
            return
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

    private fun refreshFeed() {
       downloadAllFeeds()
        getFeedsBySource()
    }
}