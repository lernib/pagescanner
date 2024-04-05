package com.lernib.pagescanner.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.lernib.pagescanner.ui.screen.CameraScreen
import com.lernib.pagescanner.ui.screen.CameraScreenProps
import com.lernib.pagescanner.ui.screen.HomeScreen
import com.lernib.pagescanner.ui.screen.HomeScreenProps
import com.lernib.pagescanner.ui.screen.ProcessImageScreen
import com.lernib.pagescanner.ui.screen.ProcessImageScreenProps

@Composable
fun NavigationComposable() {
    val (navScreen, setNavScreen) = remember {
        mutableStateOf<NavScreen>(NavScreen.Home)
    }

    fun refreshNavScreen(screen: NavScreen?) {
        val screenString = screen?.navigationCode ?: navScreen.navigationCode
        Log.i("lernib", "Navigate to $screenString")
    }

    when(navScreen) {
        NavScreen.Home -> {
            val props = HomeScreenProps(
                onNavigate = {
                    next ->
                    setNavScreen(next.toNavScreen())
                    refreshNavScreen(next.toNavScreen())
                }
            )

            HomeScreen(props)
        }

        is NavScreen.Camera -> {
            val props = CameraScreenProps(
                scans = navScreen.scans,
                onNavigate = {
                    next ->
                    setNavScreen(next.toNavScreen())
                    refreshNavScreen(next.toNavScreen())
                }
            )

            CameraScreen.Screen(props)
        }

        is NavScreen.ProcessImage -> {
            val props = ProcessImageScreenProps(
                scans = navScreen.scans,
                onNavigate = {
                    next ->
                    setNavScreen(next.toNavScreen())
                    refreshNavScreen(next.toNavScreen())
                }
            )

            ProcessImageScreen(props)
        }
    }

//    NavHost(
//        navController = navController,
//        startDestination = "home"
//    ) {
//        composable(NavScreen.Home.NAVIGATION_CODE) {
//            val props = HomeScreenProps(
//                onNavigate = {
//                    next ->
//                    setNavScreen(next.toNavScreen())
//                    refreshNavScreen(next.toNavScreen())
//                }
//            )
//
//            HomeScreen(props)
//        }
//
//        composable(NavScreen.Camera.NAVIGATION_CODE) {
//            val props = CameraScreenProps(
//                onNavigate = {
//                    next ->
//                    setNavScreen(next.toNavScreen())
//                    refreshNavScreen(next.toNavScreen())
//                }
//            )
//
//            CameraScreen(props)
//        }
//
//        composable(NavScreen.ProcessImage.NAVIGATION_CODE) {
//            if (navScreen is NavScreen.ProcessImage) {
//                val props = ProcessImageScreenProps(
//                    bitmap = navScreen.bitmap,
//                    onNavigate = { next ->
//                        setNavScreen(next.toNavScreen())
//                        refreshNavScreen(next.toNavScreen())
//                    }
//                )
//
//                ProcessImageScreen(props)
//            } else {
//                Log.e("lernib", "Loading process image without valid navScreen")
//            }
//        }
//    }
}

interface Navigation {
    fun toNavScreen(): NavScreen
}

sealed class NavScreen {
    data object Home : NavScreen() {
        const val NAVIGATION_CODE: String = "home"
    }

    data class Camera(
        val scans: MutableList<Bitmap>
    ) : NavScreen() {
        companion object {
            const val NAVIGATION_CODE: String = "camera"
        }
    }

    data class ProcessImage(
        val scans: MutableList<Bitmap>
    ) : NavScreen() {
        companion object {
            const val NAVIGATION_CODE: String = "process_image"
        }
    }

    val navigationCode: String
        get() = when(this) {
            Home -> Home.NAVIGATION_CODE
            is Camera -> Camera.NAVIGATION_CODE
            is ProcessImage -> ProcessImage.NAVIGATION_CODE
        }
}
