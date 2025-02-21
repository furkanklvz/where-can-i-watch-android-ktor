package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShowData(
    val cast: List<String> = emptyList(),
    val directors: List<String> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val id: String = "unknown",
    val imageSet: ImageSet? = null,
    val imdbId: String = "unknown",
    val itemType: String = "unknown",
    val originalTitle: String = "unknown",
    val overview: String = "unknown",
    val rating: Int = 0,
    val releaseYear: Int= 0,
    val runtime: Int= 0,
    val showType: String = "unknown",
    val streamingOptions: StreamingOptions = StreamingOptions(emptyList()),
    val title: String = "unknown",
    val tmdbId: String = "unknown"
)