package com.example.newsfeeder.base.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsfeeder.R
import com.example.newsfeeder.navigation.Screens


@Composable
fun Header(
    iconResId: Int,
    screenType: Screens
) {
    Row(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.yellow),
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
            )
            .padding(top = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = stringResource(R.string.news_feed),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
        )

        val isIconVisible = when (screenType) {
            is Screens.NewsScreen, is Screens.SourceScreen, is Screens.SavedScreen, is Screens.WebViewScreen-> false
            is Screens.DetailsScreen -> true
        }

        if (isIconVisible) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(R.string.title),
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}


