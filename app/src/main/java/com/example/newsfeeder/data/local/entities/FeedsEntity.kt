package com.example.newsfeeder.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsfeeder.domain.model.FeedModel

@Entity
data class FeedsEntity(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val image: String?,
    val pubDate: String?,
    val pubDateLong: Long?,
    val title: String?,
    val author: String?,
    val content: String?,
    val link: String?,
    val source: FeedSource?,
    val isBookmarked: Boolean
) {

    fun toModel() = FeedModel(
        id = id,
        image = image,
        pubDate = pubDate,
        title = title,
        author = author,
        content = content,
        link = link,
        source = source,
        isBookmarked = isBookmarked
    )

}