package com.klavs.wherecaniwatch.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Subscriptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.klavs.wherecaniwatch.data.entities.Buy
import com.klavs.wherecaniwatch.data.entities.Options
import com.klavs.wherecaniwatch.data.entities.WatchingOption
import com.klavs.wherecaniwatch.data.entities.WciwResponseItem
import com.klavs.wherecaniwatch.ui.theme.TrendyolAPITheme
import com.klavs.wherecaniwatch.utils.Resource
import com.klavs.wherecaniwatch.viewmodel.HomeViewModel

@Composable
fun Home(viewModel: HomeViewModel) {

    val moviesResultResource by viewModel.productsResult.collectAsStateWithLifecycle()

    HomeContent(
        moviesResultResource,
        onSearch = viewModel::getMovies
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun HomeContent(
    moviesResource: Resource<List<WciwResponseItem>>,
    onSearch: (String) -> Unit = {}
) {
    var keyword by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Home")
                }
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                TextField(keyword, onValueChange = { keyword = it },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    singleLine = true,
                    label = { Text("Movie or Series Name") },
                    trailingIcon = {
                        IconButton(
                            onClick = { onSearch(keyword) }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "search"
                            )
                        }
                    })
            }
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(10f)
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
                        val products = moviesResource.data
                        products.forEach {
                            ProductRow(it)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductRow(movie: WciwResponseItem) {
    var extended by remember { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        ListItem(
            modifier = Modifier.clickable {
                extended = !extended
            },
            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            headlineContent = { Text(movie.title, fontWeight = FontWeight.Bold) },
            overlineContent = { Text(movie.year) },
            trailingContent = {
                    Icon(
                        imageVector = if (extended) {
                            Icons.Rounded.ExpandLess
                        } else {
                            Icons.Rounded.ExpandMore
                        }, contentDescription = "expand"
                    )

            }
        )
        AnimatedVisibility(extended) {
            val optionsLabels = listOf(
                WatchingOptionLabel.BUY,
                WatchingOptionLabel.RENT,
                WatchingOptionLabel.STREAM
            )
            var selectedIndex by remember { mutableIntStateOf(0) }
            Column {
                PrimaryTabRow(
                    divider = {},
                    containerColor = Color.Transparent,
                    selectedTabIndex = selectedIndex
                ) {
                    optionsLabels.forEachIndexed { index, option ->
                        Tab(
                            selected = index == selectedIndex,
                            onClick = { selectedIndex = index },
                            icon = {
                                Icon(imageVector = when (option) {
                                    WatchingOptionLabel.BUY -> Icons.Rounded.AttachMoney
                                    WatchingOptionLabel.RENT -> Icons.Rounded.AccessTime
                                    WatchingOptionLabel.STREAM -> Icons.Rounded.Subscriptions
                                }, contentDescription = null)
                            },
                            text = {
                                Text(
                                    text = when (option) {
                                        WatchingOptionLabel.BUY -> "Buy"
                                        WatchingOptionLabel.RENT -> "Rent"
                                        WatchingOptionLabel.STREAM -> "Stream"
                                    }
                                )
                            })
                    }
                }
                when (selectedIndex) {
                    optionsLabels.indexOf(WatchingOptionLabel.BUY) -> TabContent(movie.options.buy)
                    optionsLabels.indexOf(WatchingOptionLabel.RENT) -> TabContent(movie.options.rent)
                    optionsLabels.indexOf(WatchingOptionLabel.STREAM) -> TabContent(movie.options.stream)
                }
            }
        }
        HorizontalDivider()
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TabContent(optionList: List<WatchingOption>) {
    val uriHandler = LocalUriHandler.current
    Column(Modifier.fillMaxWidth()) {
        if (optionList.isNotEmpty()) {
            optionList.forEach { option ->
                ListItem(
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    headlineContent = {
                        Text(option.provider)
                    },
                    trailingContent = {
                        IconButton(
                            modifier = Modifier.size(IconButtonDefaults.xSmallContainerSize()),
                            onClick = {
                                uriHandler.openUri(option.providerUrl)
                            },
                        ) {
                            Icon(
                                modifier = Modifier.size(IconButtonDefaults.xSmallIconSize),
                                imageVector = Icons.AutoMirrored.Rounded.OpenInNew,
                                contentDescription = "open in new"
                            )
                        }
                    },
                    supportingContent = {
                        Text(option.pricing)
                    }
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

enum class WatchingOptionLabel {
    BUY, RENT, STREAM
}

@Preview(showSystemUi = true)
@Composable
private fun HomePreview() {
    val movies = listOf(
        WciwResponseItem(
            country = "Brodie", options = Options(
                buy = listOf(
                    Buy(
                        option = "Lisette",
                        pricing = "Racquel",
                        provider = "Grayson",
                        providerUrl = "Mariella"
                    ),
                    Buy(
                        option = "Lisette",
                        pricing = "Racquel",
                        provider = "Grayson",
                        providerUrl = "Mariella"
                    )
                ),
                rent = listOf(),
                stream = listOf()
            ), title = "Tia", year = "Eldridge"
        ),
        WciwResponseItem(
            country = "Brodie", options = Options(
                buy = listOf(),
                rent = listOf(),
                stream = listOf()
            ), title = "Tia", year = "Eldridge"
        ),
        WciwResponseItem(
            country = "Brodie", options = Options(
                buy = listOf(),
                rent = listOf(),
                stream = listOf()
            ), title = "Tia", year = "Eldridge"
        )
    )

    TrendyolAPITheme(darkTheme = false) {
        HomeContent(moviesResource = Resource.Success(movies))
    }
}