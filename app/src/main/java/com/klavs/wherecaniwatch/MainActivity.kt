package com.klavs.wherecaniwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.klavs.wherecaniwatch.ui.theme.TrendyolAPITheme
import com.klavs.wherecaniwatch.view.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrendyolAPITheme {
                Navigation()
            }
        }
    }
}