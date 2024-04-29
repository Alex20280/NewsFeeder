package com.example.newsfeeder.data.local.entities

import com.example.newsfeeder.R

enum class FeedSource(val sourceString: String, val color: Int) {
    HABR("Habr", R.color.blue),
    NEXTWEB("NextWeb", R.color.red),
    TECHCRUNCH("TechCrunch", R.color.green),
    UNKNOWN("Unknown", R.color.grey);
}