package com.example.newsfeeder.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsfeeder.presentation.ui.bottomnav.source.SourceScreenViewModel
import com.example.newsfeeder.presentation.ui.details.DetailsScreen
import com.example.newsfeeder.presentation.ui.details.customtab.ChromeCustomTab
import com.example.newsfeeder.presentation.ui.bottomnav.feeds.NewsScreen
import com.example.newsfeeder.presentation.ui.bottomnav.feeds.NewsScreenViewModel
import com.example.newsfeeder.presentation.ui.bottomnav.saved.SavedNewsScreen
import com.example.newsfeeder.presentation.ui.bottomnav.saved.SavedNewsScreenViewModel
import com.example.newsfeeder.presentation.ui.bottomnav.source.SourceScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    sharedScreenViewModel: SourceScreenViewModel,
    newScreenViewModel: NewsScreenViewModel,
    savedNewsScreenViewModel: SavedNewsScreenViewModel
) {

/*
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
*/

    NavHost(navController = navController, startDestination = Screens.NewsScreen.route) {
      //  Log.d("NavigationGraphDesst", "Current screen: $currentDestination")

        composable(
            route = "news/{source}",
            arguments = listOf(
                navArgument("source") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val source = backStackEntry.arguments?.getString("source") ?: "nullsource"
            newScreenViewModel.setSource(source)
            NewsScreen(
                source = source,
                navigate = { navController.navigate("${Screens.DetailsScreen.route}/${it}") },
                newScreenViewModel = newScreenViewModel,
                paddingValues = paddingValues
            )
        }

        composable(Screens.SourceScreen.route) {
            SourceScreen(sharedViewModel = sharedScreenViewModel)
        }

        composable(Screens.SavedScreen.route) {
            SavedNewsScreen(
                savedNewsScreenViewModel = savedNewsScreenViewModel,
                navigate = { navController.navigate("${Screens.DetailsScreen.route}/${it}") }
            )
        }

        composable(
            route = Screens.DetailsScreen.getRouteWithParams("{itemId}"),
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            //val itemId = backStackEntry.arguments!!.getInt("itemId")
            DetailsScreen(
                navigate = {
                    navController.navigate(
                        "${Screens.WebViewScreen.route}?${
                            it
                        }"
                    )
                }
            )
        }

        composable(
            route = "webview?url={url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString(Constants.URL)
            ChromeCustomTab(url = url,        onBackPressed = {
                navController.popBackStack()
            })
        }
    }
}