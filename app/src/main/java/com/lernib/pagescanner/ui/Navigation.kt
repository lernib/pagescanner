package com.lernib.pagescanner.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lernib.pagescanner.ui.screen.CameraScreen
import com.lernib.pagescanner.ui.screen.CameraScreenProps
import com.lernib.pagescanner.ui.screen.HomeScreen
import com.lernib.pagescanner.ui.screen.HomeScreenProps

@Composable
fun NavigationComposable() {
    val navController = rememberNavController()
    val (navScreen, setNavScreen) = remember {
        mutableStateOf<NavScreen>(NavScreen.Home)
    }

    fun refreshNavScreen(screen: NavScreen?) {
        val screenString = screen?.navigationCode ?: navScreen.navigationCode

        Log.i("lernib", "Navigate to $screenString")

        navController.navigate(screenString)
    }
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(NavScreen.Home.NAVIGATION_CODE) {
            val props = HomeScreenProps(
                onNavigate = {
                    next ->
                    setNavScreen(next.toNavScreen())
                    refreshNavScreen(next.toNavScreen())
                }
            )

            HomeScreen(props)
        }

        composable(NavScreen.Camera.NAVIGATION_CODE) {
            val props = CameraScreenProps(
                onNavigate = {
                    next ->
                    setNavScreen(next.toNavScreen())
                    refreshNavScreen(next.toNavScreen())
                }
            )

            CameraScreen(props)
        }

        composable(NavScreen.ProcessImage.NAVIGATION_CODE) {
            Text(text = "Hello customize!")
        }
    }
}

interface Navigation {
    fun toNavScreen(): NavScreen
}

sealed class NavScreen {
    data object Home : NavScreen() {
        const val NAVIGATION_CODE: String = "home"
    }

    data object Camera : NavScreen() {
        const val NAVIGATION_CODE: String = "camera"
    }

    data class ProcessImage(val bitmap: Bitmap) : NavScreen() {
        companion object {
            const val NAVIGATION_CODE: String = "process_image"
        }
    }

    val navigationCode: String
        get() = when(this) {
            Home -> Home.NAVIGATION_CODE
            Camera -> Camera.NAVIGATION_CODE
            is ProcessImage -> ProcessImage.NAVIGATION_CODE
        }
}
