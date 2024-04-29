package com.example.newsfeeder.presentation.ui.bottomnav.source

import androidx.lifecycle.ViewModel
import com.example.newsfeeder.data.local.entities.FeedSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SourceScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow<Set<FeedSource>>(emptySet())
    val uiState get() = _uiState.asStateFlow()
    fun updateSourceList(item: FeedSource) {
        _uiState.value = if (item in _uiState.value) {
            _uiState.value - item
        } else {
            _uiState.value + item
        }
    }
}