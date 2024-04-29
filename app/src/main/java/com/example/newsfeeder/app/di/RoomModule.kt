package com.example.newsfeeder.app.di

import android.content.Context
import androidx.room.Room
import com.example.newsfeeder.data.FeedsRepositoryImpl
import com.example.newsfeeder.data.local.AppDatabase
import com.example.newsfeeder.data.local.AppDatabase.Companion.DATABASE_NAME
import com.example.newsfeeder.domain.usecase.AddFeedToFavouriteUseCase
import com.example.newsfeeder.domain.usecase.GetFeedDetailsUseCase
import com.example.newsfeeder.domain.usecase.GetLocalFavouriteFeedsUseCase
import com.example.newsfeeder.domain.usecase.RemoveFeedFromFavourites
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun getDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun getFeedDao(db: AppDatabase) = db.newsFeedsDao()

    @Provides
    fun getAddFeedToFavouriteUseCase(feedsRepository: FeedsRepositoryImpl): AddFeedToFavouriteUseCase {
        return AddFeedToFavouriteUseCase(feedsRepository)
    }

    @Provides
    fun getGetFeedDetailsUseCase(feedsRepository: FeedsRepositoryImpl): GetFeedDetailsUseCase {
        return GetFeedDetailsUseCase(feedsRepository)
    }

    @Provides
    fun getGetLocalFavouriteFeedsUseCase(feedsRepository: FeedsRepositoryImpl): GetLocalFavouriteFeedsUseCase {
        return GetLocalFavouriteFeedsUseCase(feedsRepository)
    }

    @Provides
    fun getRemoveFeedFromFavourites(feedsRepository: FeedsRepositoryImpl): RemoveFeedFromFavourites {
        return RemoveFeedFromFavourites(feedsRepository)
    }

}