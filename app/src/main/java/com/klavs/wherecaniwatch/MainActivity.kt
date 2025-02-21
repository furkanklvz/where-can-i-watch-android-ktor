package com.klavs.wherecaniwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.klavs.wherecaniwatch.ui.theme.WhereCanIWatchTheme
import com.klavs.wherecaniwatch.view.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.Transparent.toArgb()
            )
        )
        setContent {
            WhereCanIWatchTheme {
                Navigation()
            }
        }
    }
}