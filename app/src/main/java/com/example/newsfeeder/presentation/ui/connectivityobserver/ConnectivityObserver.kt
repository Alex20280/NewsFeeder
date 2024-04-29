package com.example.newsfeeder.presentation.ui.connectivityobserver

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status{
        NONE, AVAILABLE, UNAVAILABLE, LOST
    }
}