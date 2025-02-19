package com.klavs.wherecaniwatch.data.entities

sealed interface WatchingOption {
    val option: String
    val pricing: String
    val provider: String
    val providerUrl: String
}