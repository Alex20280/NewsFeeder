package com.example.newsfeeder.data.network

import com.example.newsfeeder.data.network.model.Rss
import retrofit2.Call
import retrofit2.http.GET

interface NextWebFeedApiService {

    @GET("feed/")
    fun fetchNextWebFeeds(): Call<Rss>
}