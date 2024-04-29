package com.example.newsfeeder.domain.usecase

import androidx.paging.Pager
import com.example.newsfeeder.data.local.entities.FeedsEntity
import com.example.newsfeeder.domain.FeedsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito

class GetLocalFavouriteFeedsUseCaseTest {
    private val feedsRepository: FeedsRepository = mockk()
    private val useCase = GetLocalFavouriteFeedsUseCase(feedsRepository)

    @AfterEach
    fun afterEach(){
        Mockito.reset(feedsRepository)
        Mockito.reset(useCase)
    }
    @Test
    fun `should return favorite feeds`() = runBlocking {
        // Given
        val dummyPager: Pager<Int, FeedsEntity> = mockk()
        coEvery { feedsRepository.getLocalFavouriteFeeds() } returns dummyPager

        // When
        val result = useCase.getFavouriteFeeds()

        // Then
        assertEquals(dummyPager, result)
    }
}