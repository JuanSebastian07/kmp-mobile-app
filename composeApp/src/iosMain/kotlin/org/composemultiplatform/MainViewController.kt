package org.composemultiplatform

import androidx.compose.ui.window.ComposeUIViewController
import org.composemultiplatform.di.initKoin
import org.composemultiplatform.presentation.app.App

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }
