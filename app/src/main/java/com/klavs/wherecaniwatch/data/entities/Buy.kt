package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Buy(
    override val option: String,
    override val pricing: String,
    override val provider: String,
    override val providerUrl: String
): WatchingOption