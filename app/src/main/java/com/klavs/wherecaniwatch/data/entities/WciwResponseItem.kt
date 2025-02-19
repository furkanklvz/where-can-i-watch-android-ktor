package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class WciwResponseItem(
    val country: String,
    val options: Options,
    val title: String,
    val year: String
)