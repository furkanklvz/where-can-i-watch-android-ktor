package com.klavs.wherecaniwatch.data.entities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem(val title: String, val icon: ImageVector, val route: Any) {
    data object Home: BottomBarItem(title = "Home", icon = Icons.Rounded.Home, route = Routes.Home)
    companion object{
        val items = lazy { listOf(BottomBarItem.Home) }
    }
}