package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val homePage: String = "unknown",
    val id: String = "unknown",
    val imageSet: ImageSetX = ImageSetX(),
    val name: String = "unknown",
    val themeColorCode: String = "unknown"
)