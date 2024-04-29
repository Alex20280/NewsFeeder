package com.example.newsfeeder.app.di

import com.example.newsfeeder.domain.service.FeedSourceAllocatorService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideFeedSourceAllocatorService() = FeedSourceAllocatorService()
}