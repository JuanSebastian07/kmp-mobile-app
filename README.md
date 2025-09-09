# 💸 Expenses - Kotlin Multiplatform App

**Expenses** es una aplicación de control de gastos personales construida con **Kotlin Multiplatform (KMP)** y **Compose Multiplatform (CMP)**. La app está diseñada para funcionar de forma nativa en **Android** e **iOS** desde una sola base de código, siguiendo principios modernos de arquitectura y buenas prácticas.

## 🚀 Características principales

- 📱 **Target Android & iOS** usando Kotlin Multiplatform.
- 🔄 **Lógica compartida**: toda la lógica de negocio, casos de uso y almacenamiento se comparten entre plataformas.
- 🧱 Arquitectura **Clean Architecture + MVVM**.
- ⚙️ **Inyección de dependencias** con [Koin](https://insert-koin.io/).
- 💾 Persistencia local con [SQLDelight](https://cashapp.github.io/sqldelight/).
- 🧭 Navegación nativa usando [Compose Navigation](https://developer.android.com/jetpack/compose/navigation).
- ➕ Agregar, ✏️ editar, 🗑️ eliminar y 👁️ visualizar gastos.

## 🛠️ Tecnologías

- Kotlin Multiplatform (KMP)
- Compose Multiplatform (CMP)
- SQLDelight
- Koin
- MVVM
- Clean Architecture
- Kotlinx Coroutines
- Navigation (Compose Native)

## 🧩 Módulos y estructura

> Estructura en módulo único (single module) optimizada para KMP.

⚙️ CI/CD

Este proyecto incluye una configuración de CI/CD con GitHub Actions para compilar y testear automáticamente la app en Android e iOS:

    🧪 Tests automáticos en cada push o pull request a master y develop.

    📦 Build automática del APK de Android.

    🍏 Compilación del framework y app iOS usando Xcode en runners macOS.

    ☁️ Carga de artefactos (.apk y .app) como resultados del pipeline.

<a href="https://www.youtube.com/watch?v=uU6ULXVh77M" target="_blank">
  <img src="https://github.com/JuanSebastian07/kmp-mobile-app/blob/master/Screenshots/android-ios-kmp-ui.png" alt="Descripción del Video" width="640" height="426" />
</a>



This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
