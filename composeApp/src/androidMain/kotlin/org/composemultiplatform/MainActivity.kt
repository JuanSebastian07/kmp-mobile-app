package org.composemultiplatform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.composemultiplatform.presentation.app.App
import org.composemultiplatform.presentation.app.AppViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            !appViewModel.isSplashReady.value
        }

        setContent {
            App()
        }
    }
}
