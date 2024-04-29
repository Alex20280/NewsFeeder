package com.example.newsfeeder.navigation

import com.example.newsfeeder.R

sealed class Screens(val route: String, val icon: Int){
    data object NewsScreen: Screens("news/{source}", R.drawable.ic_eye_svg)
    data object SourceScreen: Screens("source", R.drawable.ic_apps_svg)
    data object SavedScreen: Screens("saved", R.drawable.ic_bookmark_svg)
    data object DetailsScreen: Screens("details", 0)
    data object WebViewScreen : Screens("webview?url={url}", 0)

    fun getRouteWithParams(vararg params: String): String {
        val paramString = params.joinToString("/") { it }
        return "$route/$paramString"
    }
}
