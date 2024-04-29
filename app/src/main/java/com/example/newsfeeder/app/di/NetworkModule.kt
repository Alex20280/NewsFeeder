package com.example.newsfeeder.app.di

import com.example.newsfeeder.data.FeedsRepositoryImpl
import com.example.newsfeeder.data.local.AppDatabase
import com.example.newsfeeder.data.local.NewsFeedDao
import com.example.newsfeeder.data.network.NextWebFeedApiService
import com.example.newsfeeder.data.network.TechCrunchApiService
import com.example.newsfeeder.data.network.HabrFeedApiService
import com.example.newsfeeder.domain.FeedsRepository
import com.example.newsfeeder.domain.service.FeedSourceAllocatorService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addNetworkInterceptor(logger)
        return client.build()
    }

    @Singleton
    @Provides
    @Named("habrRetrofit")
    fun provideHabrRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://habr.com/")
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("nextWebRetrofit")
    fun provideNextWebRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://thenextweb.com/")
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("techCrunchRetrofit")
    fun provideTechCrunchRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://techcrunch.com/")
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideRedditFeedsApiService(@Named("habrRetrofit") retrofit: Retrofit): HabrFeedApiService {
        return retrofit.create(HabrFeedApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBloggyFeedsApiService(@Named("nextWebRetrofit") retrofit: Retrofit): NextWebFeedApiService {
        return retrofit.create(NextWebFeedApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeederFeedsApiService(@Named("techCrunchRetrofit") retrofit: Retrofit): TechCrunchApiService {
        return retrofit.create(TechCrunchApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedsRepository(
        database: AppDatabase,
        feedDao: NewsFeedDao,
        nextWebFeedApiService: NextWebFeedApiService,
        techCrunchApiService: TechCrunchApiService,
        habrFeedApiService: HabrFeedApiService,
        feedSourceAllocatorService: FeedSourceAllocatorService
    ): FeedsRepository {
        return FeedsRepositoryImpl(
            database,
            feedDao,
            nextWebFeedApiService,
            techCrunchApiService,
            habrFeedApiService,
            feedSourceAllocatorService
        )
    }
}
