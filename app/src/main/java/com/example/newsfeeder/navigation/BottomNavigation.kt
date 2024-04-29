package com.example.newsfeeder.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsfeeder.R
import com.example.newsfeeder.presentation.ui.TestTags
import com.example.newsfeeder.presentation.ui.bottomnav.source.SourceScreenViewModel

@Composable
fun BottomNavigationSection(
    navController: NavHostController,
    items: List<Screens>,
    sourceScreenViewModel: SourceScreenViewModel
) {
    BottomNavigation {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val sourceState = sourceScreenViewModel.uiState.collectAsState()

        BottomNavigationItem(
            modifier = Modifier
                .background(colorResource(id = R.color.grey))
                .fillMaxSize()
                .testTag(TestTags.NEWS_FEED_TEST_TAG),
            unselectedContentColor = colorResource(id = R.color.white),
            selectedContentColor = colorResource(id = R.color.blue),
            icon = {
                Image(
                    painter = painterResource(id = items[0].icon),
                    colorFilter = if (currentRoute == items[0].route) ColorFilter.tint(
                        colorResource(id = R.color.yellow)
                    ) else null,
                    contentDescription = stringResource(R.string.eye_icon_content)
                )
            },
            selected = currentRoute == items[0].route,
            onClick = {
                navController.navigate("news/${sourceState.value.toString()}")
            }
        )

        BottomNavigationItem(
            modifier = Modifier
                .background(colorResource(id = R.color.grey))
                .fillMaxSize()
                .testTag(TestTags.SOURCE_CLICK_TEST_TAG),
            unselectedContentColor = colorResource(id = R.color.white),
            selectedContentColor = colorResource(id = R.color.blue),
            icon = {
                Image(
                    painter = painterResource(id = items[1].icon),
                    colorFilter = if (currentRoute == items[1].route) ColorFilter.tint(
                        colorResource(id = R.color.yellow)
                    ) else null,
                    contentDescription = stringResource(R.string.source_icon_content)
                )
            },
            selected = currentRoute == items[1].route,
            onClick = {
                navController.navigate(Screens.SourceScreen.route)
            }
        )

        BottomNavigationItem(
            modifier = Modifier
                .background(colorResource(id = R.color.grey))
                .fillMaxSize()
                .testTag(TestTags.BOOKMARK_TEST_TAG),
            unselectedContentColor = colorResource(id = R.color.white),
            selectedContentColor = colorResource(id = R.color.blue),
            icon = {
                Image(
                    painter = painterResource(id = items[2].icon),
                    colorFilter = if (currentRoute == items[2].route) ColorFilter.tint(
                        colorResource(id = R.color.yellow)
                    ) else null,
                    contentDescription = stringResource(R.string.bookmark_icon_content)
                )
            },
            selected = currentRoute == items[2].route,
            onClick = {
                navController.navigate(Screens.SavedScreen.route)
            }
        )
    }
}
