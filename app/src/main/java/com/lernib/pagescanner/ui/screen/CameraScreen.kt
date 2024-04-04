package com.lernib.pagescanner.ui.screen

import androidx.compose.runtime.Composable
import com.lernib.pagescanner.ui.CameraNavigation
import com.lernib.pagescanner.ui.component.camera.CameraPreview

data class CameraScreenProps(
    val onNavigate: (CameraNavigation) -> Unit
)

@Composable
fun CameraScreen(props: CameraScreenProps) {
    CameraPreview()
}
