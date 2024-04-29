package com.example.newsfeeder.presentation.ui.bottomnav.saved

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.newsfeeder.MainActivity
import com.example.newsfeeder.presentation.ui.TestTags
import org.junit.Rule
import org.junit.Test

class BookmarkScreenUITest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testSavedFeedTextIsDisplayed() {
        composeRule.onNodeWithTag(TestTags.BOOKMARK_TEST_TAG).performClick()
        composeRule.onNodeWithText("Saved").assert(hasText("Saved"))
    }

    @Test
    fun testBookMarkButtonIsDisplayed() {
        composeRule.onAllNodesWithContentDescription("Bookmark icon")
    }

    @Test
    fun testBookMarkButtonClick() {
        composeRule.onNodeWithTag(TestTags.BOOKMARK_TEST_TAG).performClick()
    }

}