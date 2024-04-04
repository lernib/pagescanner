package com.lernib.pagescanner.ui.screen

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.lernib.pagescanner.ui.CameraNavigation
import com.lernib.pagescanner.ui.component.camera.CameraPreview
import com.lernib.pagescanner.ui.component.camera.CameraPreviewProps
import java.io.File
import java.util.Date
import java.util.Objects

data class CameraScreenProps(
    val onNavigate: (CameraNavigation) -> Unit
)

@Composable
fun CameraScreen(props: CameraScreenProps) {
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }

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
                        // TODO Take photo as soon as screen is opened
                        cameraLauncher.launch(uri)
                        props.onNavigate.invoke(CameraNavigation.CUSTOMIZE)
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

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
}
