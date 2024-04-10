package com.lernib.pagescanner.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.lernib.pagescanner.ui.NavScreen
import com.lernib.pagescanner.ui.Navigation

sealed class ProcessImageNavigation : Navigation {
    data class Camera(val props: CameraScreenProps) : ProcessImageNavigation() {
        override fun toNavScreen(): NavScreen {
            return NavScreen.Camera(props)
        }
    }

    data class Save(val props: SaveScreenProps) : ProcessImageNavigation() {
        override fun toNavScreen(): NavScreen {
            return NavScreen.Save(props)
        }
    }
}

data class ProcessImageScreenProps(
    val scans: MutableList<Bitmap>
)

@Composable
fun ProcessImageScreen(
    onNavigate: (ProcessImageNavigation) -> Unit,
    props: ProcessImageScreenProps
) {
    Box {
        Image(
            bitmap = props.scans.last().asImageBitmap(),
            contentDescription = "Camera contents"
        )

        ProcessImageOverlay(
            onKeepScanning = {
                onNavigate.invoke(
                    ProcessImageNavigation.Camera(
                        CameraScreenProps(
                            scans = props.scans
                        )
                    )
                )
            },
            onSaveScan = {
                onNavigate.invoke(
                    ProcessImageNavigation.Save(
                        SaveScreenProps(
                            scans = props.scans
                        )
                    )
                )
            }
        )
    }
}

@Composable
fun ProcessImageOverlay(
    onKeepScanning: () -> Unit,
    onSaveScan: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = onKeepScanning) {
                Text(text = "Keep scanning")
            }

            Button(onClick = onSaveScan) {
                Text(text = "Save Scan")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
