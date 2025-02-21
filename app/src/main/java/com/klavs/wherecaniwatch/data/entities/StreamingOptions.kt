package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class StreamingOptions(
    val tr: List<Tr> = emptyList()
)