package com.example.newsfeeder.presentation.ui.bottomnav.saved

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsfeeder.R
import com.example.newsfeeder.base.screens.Header
import com.example.newsfeeder.navigation.Screens
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.newsfeeder.base.poppinsFontFamily
import com.example.newsfeeder.base.text.MiddleText
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.presentation.ui.Utils

@Composable
fun SavedNewsScreen(
    savedNewsScreenViewModel: SavedNewsScreenViewModel,
    navigate:(Int) -> Unit
) {
    val source = savedNewsScreenViewModel.favouriteFeedsFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        savedNewsScreenViewModel.getFavouriteFeeds()
    }

    BookmarkUI(
        source = source,
        onItemClicked = {navigate(it)},
        addToFavourite = { savedNewsScreenViewModel.addToFavourite(it) },
        removeFromFavourite = { savedNewsScreenViewModel.removeFromFavourite(it) }
    )
}

@Composable
fun BookmarkUI(
    source: LazyPagingItems<FeedModel>,
    onItemClicked: (Int) -> Unit,
    addToFavourite: (Int) -> Unit,
    removeFromFavourite: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Header(iconResId = R.drawable.ic_bookmark_filled, screenType = Screens.SavedScreen)

        Text(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.saved),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                items(source.itemCount) { index ->
                    source[index]?.let { item ->
                        SavedItem(
                            feed = item,
                            onItemClicked = onItemClicked,
                            addToFavourite = addToFavourite,
                            removeFromFavourite = removeFromFavourite
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun SavedItem(
    feed: FeedModel,
    onItemClicked: (Int) -> Unit,
    removeFromFavourite: (Int) -> Unit,
    addToFavourite: (Int) -> Unit,
) {

    val color = Utils.getSourceColor(feed.source?.sourceString)
    val image = Utils.getSourceIcon(feed.source?.sourceString)
    val bookmarkedImagePainter = Utils.getBookMarkedIcon(feed.isBookmarked)

    Surface(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        color = colorResource(id = R.color.light_grey)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    feed.id.let { onItemClicked(it) }
                }
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .background(colorResource(id = R.color.light_grey))
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 22.dp, top = 6.dp),
                ) {
                    feed.pubDate?.let {
                        MiddleText(
                            modifier = Modifier
                                .weight(2f)
                                .align(Alignment.Bottom)
                                .padding(start = 16.dp),
                            text = Utils.getRelativeTime(it),
                            colorResId = R.color.grey_text
                        )
                    }

                    Text(
                        text = feed.source.toString(),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.W200,
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .align(Alignment.Top)
                            .background(
                                colorResource(id = color),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(bottom = 8.dp, end = 12.dp, start = 12.dp, top = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(end = 22.dp)
                ) {

                    MiddleText(
                        text = feed.title.toString(),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(2f),
                    )

                    Image(
                        painter = painterResource(id = bookmarkedImagePainter),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 18.dp, start = 24.dp)
                            .size(32.dp)
                            .clickable {
                                feed.id
                                val updatedFeed = feed.copy(isBookmarked = !feed.isBookmarked)
                                if (updatedFeed.isBookmarked) {
                                    addToFavourite(updatedFeed.id)
                                } else {
                                    removeFromFavourite(updatedFeed.id)
                                }
                            }
                    )
                }
            }
        }
    }
}