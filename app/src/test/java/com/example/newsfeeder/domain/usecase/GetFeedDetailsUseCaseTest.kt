package com.example.newsfeeder.domain.usecase

import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.domain.FeedsRepository
import com.example.newsfeeder.domain.model.FeedModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito

class GetFeedDetailsUseCaseTest {

    private val feedsRepository: FeedsRepository = mockk()

    @AfterEach
    fun afterEach(){
        Mockito.reset(feedsRepository)
    }

    @Test
    fun `should get feed details`() = runBlocking {
        val feedId = 10
        val testDetails = FeedModel(
            10,
            "image",
            "10 March",
            " title",
            "author",
            "content",
            "link",
            FeedSource.NEXTWEB,
            false
        )
        coEvery { feedsRepository.getFeedById(feedId) } returns testDetails

        // When
        val useCase = GetFeedDetailsUseCase(feedsRepository)
        val actual = useCase.getFeedDetails(feedId)

        // Then
        val expected = FeedModel(
            10,
            "image",
            "10 March",
            " title",
            "author",
            "content",
            "link",
            FeedSource.NEXTWEB,
            false
        )
        assertEquals(expected, actual)
    }
}
