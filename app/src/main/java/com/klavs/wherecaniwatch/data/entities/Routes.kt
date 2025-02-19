package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Home: Routes()
}