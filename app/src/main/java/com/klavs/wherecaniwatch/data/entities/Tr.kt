package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Tr(
    val audios: List<Audio> = emptyList(),
    val availableSince: Int = 0,
    val expiresSoon: Boolean = false,
    val link: String = "unknown",
    val quality: String = "unknown",
    val service: Service = Service(),
    val subtitles: List<Subtitle> = emptyList(),
    val type: String = "unknown"
)