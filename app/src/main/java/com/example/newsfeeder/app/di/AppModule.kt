package com.example.newsfeeder.app.di

import android.content.Context
import com.example.newsfeeder.presentation.ui.connectivityobserver.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context.applicationContext
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }
}