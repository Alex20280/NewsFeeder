package com.example.newsfeeder.presentation.ui.details.customtab

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun ChromeCustomTab(url: String?, onBackPressed: () -> Unit) {
    val isBackPressed = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(isBackPressed.value) {
        if (isBackPressed.value) {
            isBackPressed.value = false
            onBackPressed()
        }
    }

    DisposableEffect(Unit) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val droppedUrl = url?.drop(6)
        val uri: Uri = Uri.parse(droppedUrl)

        try {
            isBackPressed.value = true
            customTabsIntent.launchUrl(context, uri)
        } catch (e: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        onDispose {

        }
    }
}