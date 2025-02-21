package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class HorizontalBackdrop(
    val w1080: String = "unknown",
    val w1440: String = "unknown",
    val w360: String = "unknown",
    val w480: String = "unknown",
    val w720: String = "unknown"
)