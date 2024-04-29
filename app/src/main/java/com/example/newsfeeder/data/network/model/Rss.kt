package com.example.newsfeeder.data.network.model

import android.util.Log
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.domain.service.FeedSourceAllocatorService
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.regex.Pattern

@Root(name = "rss", strict = false)
data class Rss @JvmOverloads constructor(
    @field:Element(name = "channel")
    var channel: Chennel? = null,
)

@Root(name = "channel", strict = false)
data class Chennel (
    @field:ElementList(entry = "item", inline = true, required = false)
    var items: List<Item>? = null,
)

@Root(name = "item", strict = false)
data class Item (

    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false)
    val title: String? = null,

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    val link: String? = null,

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    val description: String? = null,

    @field:Element(name = "pubDate", required = false)
    @param:Element(name = "pubDate", required = false)
    val pubDate: String? = null,

    @field:Element(name = "creator", required = false)
    @param:Element(name = "creator", required = false)
    val creator: String? = null,

    @field:Element(name = "enclosure url", required = false)
    @param:Element(name = "enclosure url", required = false)
    val enclosure: String? = null,
) {

    fun toFeedEntity(feedSourceAllocator: FeedSourceAllocatorService): FeedsEntity {
        return FeedsEntity(
            id = 0,
            image = enclosure,
            pubDate = pubDate,
            pubDateLong = stringToLongTime(pubDate),
            title = title.removeTagsAndExtractCData(),
            author = creator,
            content = description.removeTagsAndExtractCData(),
            link = link,
            source = feedSourceAllocator.defineSource(link),
            isBookmarked = false
        )
    }
}


fun String?.removeTagsAndExtractCData(): String {
    this ?: return ""

    // Define the pattern for finding content within CDATA section and specified tags
    val pattern = "(<title>|<p>|</p>|<u>|</u>|<em>|<br>|<div>|</div>|<a href=\".*?\">|</a>|<.*?>)<!\\[CDATA\\[(.*?)\\]\\]>"

    // Replace specified tags and extract content within CDATA sections
    var contentWithoutTags = this.replace(Regex(pattern)) {
        // Return the content within CDATA section
        it.groups[2]?.value ?: ""
    }

    // Remove specified tags
    contentWithoutTags = contentWithoutTags.replace(Regex("<div>|</div>|<a href=\".*?\">|</a>|<.*?>"), "")

    // Remove HTML entities
    contentWithoutTags = contentWithoutTags.replace(Regex("&amp;|&|&nbsp;"), "")

    return contentWithoutTags
}

fun stringToLongTime(dateTimeString: String?): Long {
    if (dateTimeString.isNullOrEmpty()) return 0

    val formatter1 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss xx", Locale.ENGLISH)
    val formatter2 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)

    return try {
        val dateTime = LocalDateTime.parse(dateTimeString, formatter1)
        val zoneId = ZoneId.of("GMT")
        dateTime.atZone(zoneId).toInstant().toEpochMilli()
    } catch (e: Exception) {
        val dateTime = LocalDateTime.parse(dateTimeString, formatter2)
        val zoneId = ZoneId.of("GMT")
        dateTime.atZone(zoneId).toInstant().toEpochMilli()
    }
}