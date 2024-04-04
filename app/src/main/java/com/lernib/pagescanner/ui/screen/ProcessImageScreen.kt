package com.lernib.pagescanner.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import com.lernib.pagescanner.ui.Navigation

sealed class ProcessImageNavigation : Navigation {}

data class ProcessImageScreenProps(
    val bitmap: Bitmap,
    val onNavigate: (ProcessImageNavigation) -> Unit
)

@Composable
fun ProcessImageScreen(props: ProcessImageScreenProps) {
    Image(
        bitmap = props.bitmap.asImageBitmap(),
        contentDescription = "Camera contents"
    )
}
