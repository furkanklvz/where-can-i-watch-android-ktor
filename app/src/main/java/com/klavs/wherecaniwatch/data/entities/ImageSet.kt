package com.klavs.wherecaniwatch.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class ImageSet(
    val horizontalBackdrop: HorizontalBackdrop = HorizontalBackdrop(),
    val horizontalPoster: HorizontalPoster = HorizontalPoster(),
    val verticalBackdrop: VerticalBackdrop = VerticalBackdrop(),
    val verticalPoster: VerticalPoster = VerticalPoster()
)