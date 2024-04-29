package com.example.newsfeeder.domain.model

import com.example.newsfeeder.data.local.entities.FeedSource

data class FeedModel (
    val id: Int,
    val image: String?,
    val pubDate: String?,
    val title: String?,
    val author: String?,
    val content: String?,
    val link: String?,
    val source:FeedSource?,
    val isBookmarked: Boolean
)