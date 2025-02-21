package com.klavs.wherecaniwatch.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.ImageSearch
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.klavs.wherecaniwatch.R
import com.klavs.wherecaniwatch.data.entities.ShowData
import com.klavs.wherecaniwatch.data.entities.StreamingOptions
import com.klavs.wherecaniwatch.data.entities.Tr
import com.klavs.wherecaniwatch.data.entities.Service
import com.klavs.wherecaniwatch.ui.theme.WhereCanIWatchTheme
import com.klavs.wherecaniwatch.utils.Resource
import com.klavs.wherecaniwatch.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun Home(viewModel: HomeViewModel) {

    val moviesResultResource by viewModel.productsResult.collectAsStateWithLifecycle()

    HomeContent(
        moviesResultResource,
        onSearchOut = viewModel::getMovies,
        backToTheMain = viewModel::resetResult
    )
}

@Composable
fun ScrollingBackground(images: List<Int>, alpha: Float) {

    val state = rememberPagerState(
        pageCount = { images.size }
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            if (state.currentPage + 1 == images.size) {
                state.animateScrollToPage(
                    0,
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            } else {
                state.animateScrollToPage(
                    state.currentPage + 1,
                    animationSpec = tween(
                        durationMillis = 1000
                    )
                )
            }

        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            stringResource(R.string.app_name),
            modifier = Modifier
                .padding(top = 220.dp)
                .zIndex(2f)
                .align(Alignment.TopCenter)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    RoundedCornerShape(5.dp)
                )
                .padding(5.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        HorizontalPager(
            userScrollEnabled = false,
            state = state
        ) { count ->
            Image(
                painterResource(images[count]), contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Black)
                    .graphicsLayer(alpha = alpha),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun HomeContent(
    moviesResource: Resource<List<ShowData>>,
    onSearchOut: (String) -> Unit = {},
    backToTheMain: () -> Unit = {}
) {
    var keyword by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val cardWeight by animateFloatAsState(
        targetValue = if (moviesResource.isIdle()) 0.0001f else 10f,
        label = "Card Weight",
        animationSpec = tween(
            durationMillis = 2000,
        )
    )

    Scaffold(
        /*topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Home")
                }
            )
        }*/
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
            //.padding(top = innerPadding.calculateTopPadding())
        ) {
            val alpha by animateFloatAsState(
                targetValue = if (moviesResource.isIdle()) 0.5f else 0.25f
            )
            ScrollingBackground(
                images = List(5) {
                    listOf(
                        R.drawable.got,
                        R.drawable.avatar,
                        R.drawable.titanic,
                        R.drawable.deadpool,
                        R.drawable.prestige,
                        R.drawable.chernobyl
                    )
                }.flatten(),
                alpha = alpha
            )

            Column(
                modifier = Modifier
                    .zIndex(2f)
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    val containerColor =
                        if (moviesResource.isIdle()) TextFieldDefaults.colors().unfocusedContainerColor.copy(
                            alpha = 0.8f
                        )
                        else TextFieldDefaults.colors().unfocusedContainerColor
                    TextField(keyword, onValueChange = { keyword = it },
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = containerColor,
                            focusedContainerColor = containerColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                focusManager.clearFocus()
                                onSearchOut(keyword)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        singleLine = true,
                        leadingIcon =
                        if (!moviesResource.isIdle()) {
                            {
                                IconButton(
                                    onClick = { backToTheMain() }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            }
                        } else null,
                        label = { Text("Movie or Series Name") },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    focusManager.clearFocus()
                                    onSearchOut(keyword)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "search"
                                )
                            }
                        }
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .weight(cardWeight)
                        .clip(CardDefaults.shape)
                        .verticalScroll(rememberScrollState())
                ) {
                    when (moviesResource) {
                        is Resource.Error -> {
                            Text(moviesResource.throwable.message ?: "error")
                        }

                        Resource.Idle -> {}
                        Resource.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularWavyProgressIndicator()
                            }
                        }

                        is Resource.Success -> {
                            val shows = moviesResource.data
                            shows.forEach {
                                ShowRow(it)
                            }
                        }
                    }
                }


            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ColumnScope.ShowRow(show: ShowData) {
    var extended by remember { mutableStateOf(false) }
    val context = LocalContext.current
    ListItem(
        modifier = Modifier.clickable {
            extended = !extended
        },
        leadingContent = {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(show.imageSet?.verticalPoster?.w360)
                    .crossfade(true)
                    .build(),
                contentDescription = show.title,
                contentScale = ContentScale.Crop,
                error = {
                    Icon(
                        imageVector = Icons.Rounded.ImageSearch,
                        contentDescription = "error",
                    )
                },
                onError = {
                    Log.e("home", it.result.throwable.message ?: "")
                },
                modifier = Modifier
                    .size(
                        height = IconButtonDefaults.largeContainerSize().height,
                        width = IconButtonDefaults.largeContainerSize().height * 0.8f
                    )
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        headlineContent = { Text(show.title, fontWeight = FontWeight.Bold) },
        overlineContent = { Text(show.releaseYear.toString() + " • " + show.runtime + " dk") },
        trailingContent = {
            Icon(
                imageVector = if (extended) {
                    Icons.Rounded.ExpandLess
                } else {
                    Icons.Rounded.ExpandMore
                }, contentDescription = "expand"
            )

        },
        supportingContent = {
            val maxLines by animateIntAsState(
                targetValue = if (extended) 10 else 2
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.TrendingUp,
                        contentDescription = "rating",
                        modifier = Modifier.size(IconButtonDefaults.xSmallIconSize)
                    )
                    Text((show.rating.toFloat() / 10f).toString() + "/10")
                }
                Text(
                    show.overview,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    )
    AnimatedVisibility(
        extended,
        label = "Movie Tab",
    ) {
        TabContent(show.streamingOptions.tr)
    }
    HorizontalDivider()
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TabContent(optionList: List<Tr>) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    Column(Modifier.fillMaxWidth()) {
        if (optionList.isNotEmpty()) {
            optionList.forEach { option ->
                ListItem(
                    leadingContent = {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(option.service.imageSet.lightThemeImage)
                                .crossfade(true)
                                .build(),
                            contentDescription = option.service.name,
                            contentScale = ContentScale.Fit,
                            error = {
                                Icon(
                                    imageVector = Icons.Rounded.ErrorOutline,
                                    contentDescription = "error"
                                )
                            },
                            modifier = Modifier.size(IconButtonDefaults.smallContainerSize())
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    headlineContent = {
                        Text(option.service.name)
                    },
                    trailingContent = {
                        OutlinedButton(
                            onClick = {
                                uriHandler.openUri(option.link)
                            },
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(IconButtonDefaults.xSmallIconSize),
                                imageVector = Icons.Rounded.PlayCircleOutline,
                                contentDescription = "open in new"
                            )
                            Text("Watch", style = MaterialTheme.typography.labelSmall)
                        }
                    },
                    overlineContent = {
                        Text(option.type.replaceFirstChar { it.uppercase() })
                    },
                    supportingContent = if (option.expiresSoon) {
                        {
                            Text(
                                stringResource(R.string.expires_soon),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    } else null
                )
            }

        } else {
            Text(
                "No options available",
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomePreview() {
    val movies = listOf(
        ShowData(
            cast = listOf(),
            directors = listOf(),
            genres = listOf(),
            id = "Elijah",
            imdbId = "Neel",
            itemType = "Terrence",
            originalTitle = "Yves",
            overview = "Meleah",
            rating = 9,
            releaseYear = 2005,
            runtime = 120,
            showType = "Movie",
            streamingOptions = StreamingOptions(
                tr = listOf(
                    Tr(
                        service = Service(
                            name = "Netflix"
                        ),
                        link = "netflix.com"
                    )
                )
            ),
            title = "Dawson",
            tmdbId = "Lashay"
        ),
        ShowData(
            cast = listOf(),
            directors = listOf(),
            genres = listOf(),
            id = "Elijah",
            imdbId = "Neel",
            itemType = "Terrence",
            originalTitle = "Yves",
            overview = "Meleah",
            rating = 9476,
            releaseYear = 7863,
            runtime = 5142,
            showType = "Marli",
            streamingOptions = StreamingOptions(tr = listOf()),
            title = "Dawson",
            tmdbId = "Lashay"
        ),
        ShowData(
            cast = listOf(),
            directors = listOf(),
            genres = listOf(),
            id = "Elijah",
            imdbId = "Neel",
            itemType = "Terrence",
            originalTitle = "Yves",
            overview = "Meleah",
            rating = 9476,
            releaseYear = 7863,
            runtime = 5142,
            showType = "Marli",
            streamingOptions = StreamingOptions(tr = listOf()),
            title = "Dawson",
            tmdbId = "Lashay"
        ),
        ShowData(
            cast = listOf(),
            directors = listOf(),
            genres = listOf(),
            id = "Elijah",
            imdbId = "Neel",
            itemType = "Terrence",
            originalTitle = "Yves",
            overview = "Meleah",
            rating = 9476,
            releaseYear = 7863,
            runtime = 5142,
            showType = "Marli",
            streamingOptions = StreamingOptions(tr = listOf()),
            title = "Dawson",
            tmdbId = "Lashay"
        ),
        ShowData(
            cast = listOf(),
            directors = listOf(),
            genres = listOf(),
            id = "Elijah",
            imdbId = "Neel",
            itemType = "Terrence",
            originalTitle = "Yves",
            overview = "Meleah",
            rating = 9476,
            releaseYear = 7863,
            runtime = 5142,
            showType = "Marli",
            streamingOptions = StreamingOptions(tr = listOf()),
            title = "Dawson",
            tmdbId = "Lashay"
        ),
        ShowData(
            cast = listOf(),
            directors = listOf(),
            genres = listOf(),
            id = "Elijah",
            imdbId = "Neel",
            itemType = "Terrence",
            originalTitle = "Yves",
            overview = "Meleah",
            rating = 9476,
            releaseYear = 7863,
            runtime = 5142,
            showType = "Marli",
            streamingOptions = StreamingOptions(tr = listOf()),
            title = "Dawson",
            tmdbId = "Lashay"
        ),
        ShowData(
            cast = listOf(),
            directors = listOf(),
            genres = listOf(),
            id = "Elijah",
            imdbId = "Neel",
            itemType = "Terrence",
            originalTitle = "Yves",
            overview = "Meleah",
            rating = 9476,
            releaseYear = 7863,
            runtime = 5142,
            showType = "Marli",
            streamingOptions = StreamingOptions(tr = listOf()),
            title = "Dawson",
            tmdbId = "Lashay"
        )
    )

    var resource by remember { mutableStateOf<Resource<List<ShowData>>>(Resource.Idle) }
    var resource2 by remember { mutableStateOf(Resource.Success(movies)) }

    WhereCanIWatchTheme(darkTheme = false) {
        HomeContent(moviesResource = resource2,
            onSearchOut = {
                resource = Resource.Success(data = movies)
            })
    }
}