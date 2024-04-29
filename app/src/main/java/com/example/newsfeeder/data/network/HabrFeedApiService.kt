package com.example.newsfeeder.data.network

import com.example.newsfeeder.data.network.model.Rss
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HabrFeedApiService {

    @GET("ru/rss/feed/f1642b7f3193a0c6da52c3ccb0168302?fl=ru&types%5B%5D=article")
    fun fetchHabrFeeds(@Query("limit") limit: Int): Call<Rss>


}