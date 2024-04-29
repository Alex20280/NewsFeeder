package com.example.newsfeeder.presentation.ui.bottomnav.feeds

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

class NewsFeedUITest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAllFeedsIsDisplayed() {
        composeRule.onNodeWithText("All feeds").assert(hasText("All feeds"))
    }

    @Test
    fun testNewsIconClick() {
        composeRule.onNodeWithTag(TestTags.BOOKMARK_TEST_TAG).performClick()
        composeRule.onNodeWithTag(TestTags.NEWS_FEED_TEST_TAG).performClick()
    }

    @Test
    fun testNewsFeedIsDisplayed() {
        composeRule.onNodeWithText("News Feed").assert(hasText("News Feed"))
    }

    @Test
    fun testEyeButtonIsDisplayed() {
        composeRule.onAllNodesWithContentDescription("Eye icon")
    }

}