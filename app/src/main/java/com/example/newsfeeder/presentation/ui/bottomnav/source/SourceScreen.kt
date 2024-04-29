package com.example.newsfeeder.presentation.ui.bottomnav.source

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsfeeder.R
import com.example.newsfeeder.base.screens.Header
import com.example.newsfeeder.navigation.Screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.newsfeeder.data.local.entities.FeedSource

@Composable
fun SourceScreen(
    sharedViewModel: SourceScreenViewModel,
) {

    val state = sharedViewModel.uiState.collectAsState()

    SourceUI(
        state = state,
        updateSourceList = { sharedViewModel.updateSourceList(it) }
    )
}

@Composable
fun SourceUI(
    state: State<Set<FeedSource>>,
    updateSourceList: (FeedSource) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Header(iconResId = R.drawable.ic_bookmark_empty, screenType = Screens.SourceScreen)

        Text(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.all_groups),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        SourceItemRow(
            state = state,
            items = FeedSource.entries.toTypedArray().toList(),
            updateSourceList = updateSourceList
        )
    }
}

@Composable
fun SourceItemRow(
    state: State<Set<FeedSource>>,
    items: List<FeedSource>,
    updateSourceList: (FeedSource) -> Unit,
) {

    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.dropLast(1)) { source ->
            val isSelected = source in state.value
            Text(
                text = source.sourceString,
                style = MaterialTheme.typography.bodyLarge.copy(colorResource(id = R.color.white)),
                modifier = Modifier
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = if (isSelected) Color.Black else Color.White,
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(
                        colorResource(id = source.color),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(8.dp)
                    .clickable {
                        if (isSelected) {
                            state.value - source
                        } else {
                            state.value + source
                        }
                        updateSourceList(source)
                    }
            )
        }
    }
}