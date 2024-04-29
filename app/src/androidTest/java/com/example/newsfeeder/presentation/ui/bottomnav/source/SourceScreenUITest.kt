package com.example.newsfeeder.presentation.ui.bottomnav.source

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.newsfeeder.MainActivity
import com.example.newsfeeder.presentation.ui.TestTags
import org.junit.Rule
import org.junit.Test

class SourceScreenUITest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAllGroupsTextIsDisplayed() {
        composeRule.onNodeWithTag(TestTags.SOURCE_CLICK_TEST_TAG).performClick()
        composeRule.onNodeWithText("All groups").assert(hasText("All groups"))
    }

    @Test
    fun testSourceButtonClick() {
        composeRule.onNodeWithTag(TestTags.SOURCE_CLICK_TEST_TAG).performClick()
    }

    @Test
    fun testSourceItemsClick() {
        composeRule.onNodeWithTag(TestTags.SOURCE_CLICK_TEST_TAG).performClick()
        composeRule.onNodeWithText("Habr").performClick()
        composeRule.onNodeWithText("NextWeb").performClick()
        composeRule.onNodeWithText("TechCrunch").performClick()
    }

    @Test
    fun testSourceButtonsAreDisplayed() {
        composeRule.onNodeWithTag(TestTags.SOURCE_CLICK_TEST_TAG).performClick()
        composeRule.onNodeWithText("Habr").assert(hasText("Habr"))
        composeRule.onNodeWithText("NextWeb").assert(hasText("NextWeb"))
        composeRule.onNodeWithText("TechCrunch").assert(hasText("TechCrunch"))
    }


}