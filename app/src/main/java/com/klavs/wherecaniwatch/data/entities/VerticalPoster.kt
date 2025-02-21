package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class VerticalPoster(
    val w240: String = "",
    val w360: String = "",
    val w480: String = "",
    val w600: String = "",
    val w720: String = ""
)