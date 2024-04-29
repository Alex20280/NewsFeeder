package com.example.newsfeeder.base.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsfeeder.R

@Composable
fun SmallText(
    text: String,
    modifier: Modifier = Modifier,
    colorResId: Int = R.color.grey
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(color = colorResource(id = colorResId))
    )
}

@Composable
fun MiddleText(
    text: String,
    modifier: Modifier = Modifier,
    colorResId: Int = R.color.black
) {
    Text(
        modifier = modifier,
        fontSize = 13.sp,
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(color = colorResource(id = colorResId)).copy(fontWeight = FontWeight.Bold),
    )
}

@Composable
fun LargeText(
    text: String,
    modifier: Modifier = Modifier,
    colorResId: Int = R.color.black
) {
    Text(
        modifier = modifier,
        fontSize = 20.sp,
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(color = colorResource(id = colorResId)).copy(fontWeight = FontWeight.Bold),
    )
}