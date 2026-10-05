package com.example.watchventure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.watchventure.navigation.AppNavigation
import com.example.watchventure.ui.theme.TVExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVExplorerTheme {
                AppNavigation()
            }
        }
    }
}
