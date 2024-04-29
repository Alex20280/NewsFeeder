package com.example.newsfeeder.presentation.ui.bottomnav.source

import com.example.newsfeeder.data.local.entities.FeedSource
import com.example.newsfeeder.presentation.ui.bottomnav.source.SourceScreenViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SourceScreenViewModelTest() {

    private lateinit var sourceScreenViewModel: SourceScreenViewModel

    @Before
    fun setUp() {
        sourceScreenViewModel = SourceScreenViewModel()
    }

    @Test
    fun testUpdateSourceList() = runBlocking {
        // Initial state should be empty
        assertEquals(emptySet<FeedSource>(), sourceScreenViewModel.uiState.first())

        // Update source list with a feed source
        sourceScreenViewModel.updateSourceList(FeedSource.HABR)
        assertEquals(setOf(FeedSource.HABR), sourceScreenViewModel.uiState.first())

        // Adding the same feed source again should remove it
        sourceScreenViewModel.updateSourceList(FeedSource.HABR)
        assertEquals(emptySet<FeedSource>(), sourceScreenViewModel.uiState.first())

        // Adding a different feed source
        sourceScreenViewModel.updateSourceList(FeedSource.NEXTWEB)
        assertEquals(setOf(FeedSource.NEXTWEB), sourceScreenViewModel.uiState.first())
    }

}