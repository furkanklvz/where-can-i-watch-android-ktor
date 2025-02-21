package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Subtitle(
    val closedCaptions: Boolean = false,
    val locale: Locale = Locale("en")
)