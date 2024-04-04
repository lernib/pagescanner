package com.lernib.pagescanner.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lernib.pagescanner.ui.screen.CameraScreen
import com.lernib.pagescanner.ui.screen.CameraScreenProps
import com.lernib.pagescanner.ui.screen.HomeScreen
import com.lernib.pagescanner.ui.screen.HomeScreenProps

enum class HomeNavigation {
    CAMERA
}

enum class CameraNavigation {
    CUSTOMIZE
}

@Composable
fun Navigation() {
    val navController = rememberNavController();
    
    NavHost(
        navController = navController,
        startDestination = "HOME"
    ) {
        composable("HOME") {
            val props = HomeScreenProps(
                onNavigate = {
                    next ->
                    navController.navigate(next.toString())
                }
            )

            HomeScreen(props)
        }

        composable("CAMERA") {
            val props = CameraScreenProps(
                onNavigate = {
                    next ->
                    navController.navigate(next.toString())
                }
            )

            CameraScreen(props)
        }
    }
}
