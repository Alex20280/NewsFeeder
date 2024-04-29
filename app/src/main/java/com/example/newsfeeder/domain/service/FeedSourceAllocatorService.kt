package com.example.newsfeeder.domain.service

import com.example.newsfeeder.data.local.entities.FeedSource

class FeedSourceAllocatorService() {

    fun defineSource(url: String?): FeedSource {
        return when {
            url?.contains("habr.com") == true -> FeedSource.HABR
            url?.contains("thenextweb.com") == true -> FeedSource.NEXTWEB
            url?.contains("techcrunch.com") == true -> FeedSource.TECHCRUNCH
            else -> FeedSource.UNKNOWN
        }
    }
}