package com.example.newsfeeder

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.newsfeeder.navigation.BottomNavigationSection
import com.example.newsfeeder.navigation.NavigationGraph
import com.example.newsfeeder.navigation.Screens
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsfeeder.presentation.ui.bottomnav.source.SourceScreenViewModel
import com.example.newsfeeder.presentation.ui.bottomnav.feeds.NewsScreenViewModel
import com.example.newsfeeder.presentation.ui.bottomnav.saved.SavedNewsScreenViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView() {


    val sharedScreenViewModel: SourceScreenViewModel = hiltViewModel()
    val newScreenViewModel: NewsScreenViewModel = hiltViewModel()
    val savedNewsScreenViewModel: SavedNewsScreenViewModel = hiltViewModel()

    val screens = listOf(
        Screens.NewsScreen,
        Screens.SourceScreen,
        Screens.SavedScreen,
    )

    val navController = rememberNavController()
    var bottomBarVisible by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (bottomBarVisible) {
                BottomNavigationSection(
                    navController = navController,
                    items = screens,
                    sourceScreenViewModel = sharedScreenViewModel
                )
            }
        },
    ) {

        NavigationGraph(
            navController = navController,
            paddingValues = it,
            sharedScreenViewModel = sharedScreenViewModel,
            newScreenViewModel = newScreenViewModel,
            savedNewsScreenViewModel = savedNewsScreenViewModel,
        )
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        bottomBarVisible = when (destination.route) {
            Screens.NewsScreen.route, Screens.SourceScreen.route, Screens.SavedScreen.route -> {
                true
            }

            else -> {
                false
            }
        }
    }
}