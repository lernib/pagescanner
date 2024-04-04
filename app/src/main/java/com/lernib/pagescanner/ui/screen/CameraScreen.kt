package com.lernib.pagescanner.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lernib.pagescanner.ui.NavScreen
import com.lernib.pagescanner.ui.Navigation
import com.lernib.pagescanner.ui.component.camera.CameraPreview
import com.lernib.pagescanner.ui.component.camera.CameraPreviewProps

sealed class CameraNavigation : Navigation {
    data class ProcessImage(val bitmap: Bitmap) : CameraNavigation()

    override fun toNavScreen(): NavScreen {
        return when(this) {
            is ProcessImage -> NavScreen.ProcessImage(bitmap)
        }
    }
}

data class CameraScreenProps(
    val onNavigate: (CameraNavigation) -> Unit
)

@Composable
fun CameraScreen(props: CameraScreenProps) {
    val context = LocalContext.current
    val previewView = remember {
        PreviewView(context)
    }

    Box {
        CameraPreview(CameraPreviewProps(
            previewView = previewView
        ))

        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row {
                Spacer(modifier = Modifier.weight(1f))
                LargeFloatingActionButton(
                    onClick = {
                        val bitmap = previewView.bitmap

                        if (bitmap != null)
                            props.onNavigate.invoke(CameraNavigation.ProcessImage(bitmap))

                        Log.i("lernib", "Bitmap is null")
                    },
                    modifier = Modifier
                        .size(75.dp)
                ) {
                    Icon(
                        Icons.Filled.Done,
                        "Done"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
