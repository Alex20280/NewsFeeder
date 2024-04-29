package com.example.newsfeeder.presentation.ui.details

import androidx.lifecycle.SavedStateHandle
import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.domain.usecase.FeedsUseCase
import com.example.newsfeeder.domain.usecase.GetFeedDetailsUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest() {

    private lateinit var detailsViewModel: DetailsViewModel
    val getFeedDetailsUseCase = mockk<GetFeedDetailsUseCase>()
    val savedStateHandle = mockk<SavedStateHandle>()
    val feedsUseCase = mockk<FeedsUseCase>()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        every { savedStateHandle.get<Int>(any()) } returns 123
        detailsViewModel = DetailsViewModel(getFeedDetailsUseCase, savedStateHandle, feedsUseCase)
    }

    @Test
    fun toggleBookmarkShouldUpdateFeedDetails()  {
        // Given
        val initialFeed = FeedModel(
            id = 123,
            image = "image",
            pubDate = "pubDate",
            title = "title",
            author = "author",
            content = "content",
            link = "link",
            source = FeedSource.HABR,
            isBookmarked = false
        )

        val updatedFeed = initialFeed.copy(isBookmarked = true)

        // When
        detailsViewModel.toggleBookmark(initialFeed)

        // Then
        val actualFeed = detailsViewModel.feedDetails.value
        assertEquals(updatedFeed, actualFeed)
    }

}