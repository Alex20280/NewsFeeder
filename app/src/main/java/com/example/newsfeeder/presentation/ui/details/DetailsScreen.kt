package com.example.newsfeeder.presentation.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsfeeder.R
import com.example.newsfeeder.base.text.LargeText
import com.example.newsfeeder.base.text.SmallText
import com.example.newsfeeder.domain.model.FeedModel
import com.example.newsfeeder.presentation.ui.Utils
import java.util.regex.Pattern

@Composable
fun DetailsScreen(
    navigate:(String) -> Unit
) {

    val viewModel = hiltViewModel<DetailsViewModel>()
    val screenDetails = viewModel.feedDetails.collectAsState()

    DetailsUI(
        screenDetails = screenDetails,
        onTextClicked = {navigate(screenDetails.value.link?.toUri()?.toString() ?: "")},
        onIconClicked = {viewModel.toggleBookmark(it)}
    )
}

@Composable
fun DetailsUI(
    screenDetails:  State<FeedModel>,
    onTextClicked: (String?) -> Unit,
    onIconClicked: (FeedModel) -> Unit
) {
    val isLoading by remember { mutableStateOf(false) }
    val image = Utils.getSourceIcon(screenDetails.value.source?.sourceString)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        DetailsScreenHeader(screenDetails = screenDetails, onIconClicked = onIconClicked)

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.yellow)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                SmallText(
                    text = Utils.getRelativeTime(screenDetails.value.pubDate.toString()),
                    modifier = Modifier
                        .padding(top = 26.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .testTag("itemPubTime"),
                )

                LargeText(
                    text = screenDetails.value.title.toString(),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                )

                AuthorAttributedText(author = screenDetails.value.author.toString())

                Image(
                    painter = painterResource(id = image),
                    contentDescription = stringResource(R.string.image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )

                HighlightedText(
                    text = screenDetails.value.content.toString(),
                    modifier = Modifier
                        .padding(16.dp),
                    onTextClicked =
                    { onTextClicked(screenDetails.value.link?.toUri()?.toString()?: "") }
                )
            }
        }
    }
}

@Composable
fun AuthorAttributedText(
    author: String
) {
    val text = "By $author"
    val firstCharacter: Int = 3
    val lastCharacter: Int = text.length

    val annotatedString = buildAnnotatedString {
        withStyle(style = MaterialTheme.typography.bodySmall.toSpanStyle()) {
            append(text)
        }
        addStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.yellow)
            ),
            start = firstCharacter,
            end = lastCharacter
        )
    }

    Text(
        text = annotatedString,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    )
}

fun isRussian(text: String): Boolean {
    val cyrillicPattern = Pattern.compile("\\p{InCyrillic}")
    return cyrillicPattern.matcher(text).find()
}

@Composable
fun HighlightedText(
    text: String,
    modifier: Modifier = Modifier,
    onTextClicked: () -> Unit = {}
) {
    val isRussian = isRussian(text)

    val annotatedString = buildAnnotatedString {
        append(text)
        if (isRussian) {
            val lastTwoWords = text.split(" ").takeLast(2).joinToString(" ")
            addStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.yellow),
                    textDecoration = TextDecoration.Underline
                ),
                start = text.length - lastTwoWords.length,
                end = text.length
            )
        } else {
            append(" ")
            addStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.yellow),
                    textDecoration = TextDecoration.Underline
                ),
                start = text.length,
                end = text.length + "Read more".length + 1
            )
            append("Read more")
        }
    }

    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clickable {
                onTextClicked()
            }
    )
}

@Composable
fun DetailsScreenHeader(
    screenDetails: State<FeedModel?>,
    onIconClicked: (FeedModel) -> Unit = {}
) {

    val bookmarkedImagePainter: Painter = if (screenDetails.value?.isBookmarked == true) {
        painterResource(id = R.drawable.ic_bookmark_svg_black)
    } else {
        painterResource(id = R.drawable.ic_bookmark_empty_svg)
    }

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

        Image(
            painter = bookmarkedImagePainter,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable {
                    screenDetails.value?.let { onIconClicked(it) }
                }
        )
    }
}
