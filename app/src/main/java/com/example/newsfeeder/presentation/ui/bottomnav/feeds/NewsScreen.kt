package com.example.newsfeeder.presentation.ui.bottomnav.feeds

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Snackbar
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.newsfeeder.R
import com.example.newsfeeder.base.screens.Header
import com.example.newsfeeder.navigation.Screens
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsfeeder.base.poppinsFontFamily
import com.example.newsfeeder.base.text.MiddleText
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.presentation.ui.Utils

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsScreen(
    source: String,
    newScreenViewModel: NewsScreenViewModel,
    navigate: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val screenSource by newScreenViewModel.sourceStatus.collectAsState()
    val feedBySource = newScreenViewModel.getFeedsFlow().collectAsLazyPagingItems()
    val isRefreshing by newScreenViewModel.isRefreshing.collectAsState()
    val isInternetAvailabilitySnackBarVisible by newScreenViewModel.isSnackBarVisible.collectAsState()
    val isInternetOn by newScreenViewModel.isInternetOn.collectAsState()
    var isFirstRun by remember { mutableStateOf(true) }

    LaunchedEffect(screenSource) {
        newScreenViewModel.getFeedsBySource()
    }

    LaunchedEffect(Unit) {
        newScreenViewModel.observerNetworkConnectivity()
    }

    LaunchedEffect(key1 = source) {
        newScreenViewModel.setSource(source)
    }

    LaunchedEffect(key1 = !isRefreshing) {
        if (!isFirstRun && !isInternetOn) {
            Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show()
        }
        isFirstRun = false
    }

    NewsUI(
        isInternetOn = isInternetOn,
        isRefreshing = isRefreshing,
        feedBySource = feedBySource,
        isInternetAvailabilitySnackBarVisible = isInternetAvailabilitySnackBarVisible,
        paddingValues = paddingValues,
        onItemClicked = { navigate(it) },
        addToFavourite = { newScreenViewModel.addToFavourite(it) },
        removeFromFavourite = { newScreenViewModel.removeFromFavourite(it) },
        onDismissSnackBar = { newScreenViewModel.dismissSnackBar() },
        pullOnRefresh = { newScreenViewModel.refresh() }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsUI(
    isInternetOn: Boolean,
    isRefreshing: Boolean,
    isInternetAvailabilitySnackBarVisible: Boolean,
    feedBySource: LazyPagingItems<FeedModel>,
    pullOnRefresh: () -> Unit,
    paddingValues: PaddingValues,
    onItemClicked: (Int) -> Unit,
    addToFavourite: (Int) -> Unit,
    removeFromFavourite: (Int) -> Unit,
    onDismissSnackBar: () -> Unit
) {

    val emptyItemCount = 0
    val state = rememberPullRefreshState(isRefreshing, pullOnRefresh)

    Column(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())
            .fillMaxSize(),
    ) {

        Header(iconResId = R.drawable.ic_bookmark_empty, screenType = Screens.NewsScreen)

        Text(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 8.dp),
            text = stringResource(R.string.all_feeds),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        if (feedBySource.itemCount == emptyItemCount) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = colorResource(id = R.color.yellow),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        } else {
            Box(Modifier.pullRefresh(state)) {

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        Modifier.padding().testTag("lazyColumn")
                    ) {
                        items(feedBySource.itemCount) { index ->
                            feedBySource[index]?.let { item ->
                                NewsItem(
                                    feed = item,
                                    onItemClicked = onItemClicked,
                                    addToFavourite = addToFavourite,
                                    removeFromFavourite = removeFromFavourite
                                )
                            }
                        }
                    }
                    if (isInternetAvailabilitySnackBarVisible) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            SetSnackBar(
                                onRefreshClick = onDismissSnackBar,
                                isInternetOn = isInternetOn
                            )
                        }
                    }
                }

                PullRefreshIndicator(isRefreshing, state, Modifier.align(Alignment.TopCenter))

            }
        }

    }
}

@Composable
fun SetSnackBar(
    onRefreshClick: () -> Unit,
    isInternetOn: Boolean
) {
    val snackBarText =
        if (isInternetOn) stringResource(R.string.snackbar_internet_is_on) else stringResource(R.string.snackbar_internet_is_off)
    val snackBarTextColor = if (isInternetOn) R.color.green else R.color.red
    val snackBarButtonText = if (isInternetOn) stringResource(R.string.snackbar_refresh) else stringResource(R.string.snackbar_dismiss)

    Snackbar(
        action = {
            TextButton(
                onClick = {
                    onRefreshClick()
                }

            ) {
                Text(
                    text = snackBarButtonText,
                    color = colorResource(id = R.color.yellow)
                )
            }
        },
        content = {
            Text(
                color = colorResource(id = snackBarTextColor),
                text = snackBarText
            )
        },
        backgroundColor = colorResource(id = R.color.black)
    )
}

@Composable
fun NewsItem(
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
                .testTag("itemClick")
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
                            .weight(2f)
                    )

                    Image(
                        painter = painterResource (bookmarkedImagePainter),
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

