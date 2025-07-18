package org.composemultiplatform

import androidx.compose.ui.window.ComposeUIViewController
import org.composemultiplatform.di.appModule
import org.composemultiplatform.di.initKoin
import org.composemultiplatform.presentation.App
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }
