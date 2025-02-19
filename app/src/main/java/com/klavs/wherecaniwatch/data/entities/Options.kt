package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Options(
    val buy: List<Buy> = emptyList(),
    val rent: List<Rent> = emptyList(),
    val stream: List<Stream> = emptyList()
)