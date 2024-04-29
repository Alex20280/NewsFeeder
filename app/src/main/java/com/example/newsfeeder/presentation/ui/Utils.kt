package com.example.newsfeeder.presentation.ui

import com.example.newsfeeder.R
import com.example.newsfeeder.data.local.entities.FeedSource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object Utils {
    fun getRelativeTime(dateString: String): String {
        return runCatching {
            val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
            val date = inputFormat.parse(dateString)
                ?: throw IllegalArgumentException("Invalid Date")

            val currentTime = Date()
            val diffMillis = abs(currentTime.time - date.time)
            val diffSeconds = diffMillis / 1000
            val diffMinutes = diffSeconds / 60
            val diffHours = diffMinutes / 60
            val diffDays = diffHours / 24
            val diffWeeks = diffDays / 7
            val diffMonths = diffDays / 30
            val diffYears = diffDays / 365

            when {
                diffYears >= 1 -> "Last year"
                diffMonths >= 1 -> "Last month"
                diffWeeks >= 1 -> "Last week"
                diffDays >= 1 -> "Yesterday"
                diffHours >= 1 -> "$diffHours hours ago"
                diffMinutes >= 1 -> "$diffMinutes minutes ago"
                else -> "Just now"
            }
        }.getOrElse {
            "Invalid Date"
        }
    }

    fun getSourceColor(sourceString: String?): Int {
        return when (sourceString) {
            FeedSource.HABR.sourceString -> R.color.blue
            FeedSource.TECHCRUNCH.sourceString -> R.color.green
            FeedSource.NEXTWEB.sourceString -> R.color.red
            else -> R.color.light_grey
        }
    }

    fun getSourceIcon(sourceString: String?): Int {
        return when (sourceString) {
            FeedSource.HABR.sourceString -> R.drawable.ic_habr_icon
            FeedSource.NEXTWEB.sourceString -> R.drawable.ic_nextweb_icon
            FeedSource.TECHCRUNCH.sourceString -> R.drawable.ic_ct_icon
            else -> R.drawable.ic_rose
        }
    }

    fun getBookMarkedIcon(isBookmarked: Boolean?): Int {
        return if (isBookmarked == true) {
            R.drawable.ic_bookmark_filled
        } else {
            R.drawable.ic_bookmark_empty
        }
    }
}