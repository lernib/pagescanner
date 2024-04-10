package com.lernib.pagescanner.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.lernib.pagescanner.ui.screen.CameraScreen
import com.lernib.pagescanner.ui.screen.CameraScreenProps
import com.lernib.pagescanner.ui.screen.HomeScreen
import com.lernib.pagescanner.ui.screen.ListImagesScreen
import com.lernib.pagescanner.ui.screen.ListImagesScreenProps
import com.lernib.pagescanner.ui.screen.ProcessImageScreen
import com.lernib.pagescanner.ui.screen.ProcessImageScreenProps
import com.lernib.pagescanner.ui.screen.SaveScreen
import com.lernib.pagescanner.ui.screen.SaveScreenProps

@Composable
fun NavigationComposable() {
    val (navScreen, setNavScreen) = remember {
        mutableStateOf<NavScreen>(NavScreen.Home)
    }

    fun refreshNavScreen(screen: NavScreen?) {
        val screenString = screen?.navigationCode ?: navScreen.navigationCode
        Log.i("lernib", "Navigate to $screenString")
    }

    fun<T: Navigation> onNavigate(navigate: T) {
        setNavScreen(navigate.toNavScreen())
        refreshNavScreen(navigate.toNavScreen())
    }

    when(navScreen) {
        NavScreen.Home -> {
            HomeScreen(onNavigate = { next -> onNavigate(next) })
        }

        is NavScreen.Camera -> {
            CameraScreen.Screen(
                props = navScreen.props,
                onNavigate = { next -> onNavigate(next) }
            )
        }

        is NavScreen.ProcessImage -> {
            ProcessImageScreen(
                props = navScreen.props,
                onNavigate = { next -> onNavigate(next) }
            )
        }

        is NavScreen.ListImages -> {
            ListImagesScreen.Screen(
                props = navScreen.props,
                onNavigate = { next -> onNavigate(next) }
            )
        }

        is NavScreen.Save -> {
            SaveScreen.Screen(
                navigate = { next -> onNavigate(next) },
                props = navScreen.props
            )
        }
    }
}

interface Navigation {
    fun toNavScreen(): NavScreen
}

sealed class NavScreen(
    val navigationCode: String
) {
    data object Home : NavScreen("home")
    data class Camera(val props: CameraScreenProps) : NavScreen("camera")
    data class ProcessImage(val props: ProcessImageScreenProps) : NavScreen("process_image")
    data class ListImages(val props: ListImagesScreenProps) : NavScreen("list_images")
    data class Save(val props: SaveScreenProps) : NavScreen("save")
}
