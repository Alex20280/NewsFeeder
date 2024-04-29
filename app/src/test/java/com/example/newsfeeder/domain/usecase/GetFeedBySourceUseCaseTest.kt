package com.example.newsfeeder.domain.usecase

import androidx.paging.Pager
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.domain.FeedsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito

class GetFeedBySourceUseCaseTest {

    private val repository: FeedsRepository = mockk()
    private val useCase = GetFeedBySourceUseCase(repository)

    @AfterEach
    fun afterEach(){
        Mockito.reset(repository)
        Mockito.reset(useCase)
    }

    @Test
    fun `should get feed by source`() = runBlocking {
        // Given
        val feedSources = listOf(FeedSource.NEXTWEB)
        val dummyPager: Pager<Int, FeedsEntity> = mockk()
        coEvery { repository.getFeedsBySource(feedSources) } returns dummyPager

        // When
        val result = useCase.getFeedsBySource(feedSources)

        // Then
        assertEquals(dummyPager, result)
    }
}
