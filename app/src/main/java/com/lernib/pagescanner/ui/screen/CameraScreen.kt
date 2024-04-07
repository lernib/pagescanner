package com.lernib.pagescanner.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lernib.pagescanner.ui.NavScreen
import com.lernib.pagescanner.ui.Navigation
import com.lernib.pagescanner.ui.component.camera.CameraPreview
import com.lernib.pagescanner.ui.component.camera.CameraPreviewProps
import com.lernib.pagescanner.ui.theme.Purple80

sealed class CameraNavigation : Navigation {
    data class ProcessImage(val props: ProcessImageScreenProps) : CameraNavigation()
    data class ListImages(val props: ListImagesScreenProps) : CameraNavigation()

    override fun toNavScreen(): NavScreen {
        return when(this) {
            is ProcessImage -> NavScreen.ProcessImage(props)
            is ListImages -> NavScreen.ListImages(props)
        }
    }
}

data class CameraScreenProps(
    val scans: MutableList<Bitmap>
)

object CameraScreen {
    @Composable
    fun Screen(
        onNavigate: (CameraNavigation) -> Unit,
        props: CameraScreenProps
    ) {
        val context = LocalContext.current
        val previewView = remember {
            PreviewView(context)
        }

        Box {
            CameraPreview(
                CameraPreviewProps(
                    previewView = previewView
                )
            )

            Interactive(
                count = props.scans.count(),
                onSnap = {
                    val bitmap = previewView.bitmap

                    if (bitmap != null) {
                        props.scans.add(bitmap)
                        onNavigate.invoke(
                            CameraNavigation.ProcessImage(
                                ProcessImageScreenProps(
                                    scans = props.scans
                                )
                            )
                        )
                    } else {
                        Log.i("lernib", "Bitmap is null")
                    }
                },
                onListImages = {
                    onNavigate.invoke(
                        CameraNavigation.ListImages(
                            ListImagesScreenProps(
                                scans = props.scans
                            )
                        )
                    )
                }
            )
        }
    }

    @Composable
    fun Interactive(
        onSnap: () -> Unit,
        onListImages: () -> Unit,
        count: Int
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Max)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                LargeFloatingActionButton(
                    onClick = onSnap,
                    modifier = Modifier
                        .size(75.dp)
                ) {
                    Icon(
                        Icons.Filled.Done,
                        "Done"
                    )
                }

                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    
                    if (count > 0) {
                        Button(
                            onClick = onListImages,
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .aspectRatio(1f)
                        ) {
                            CountText(count = count, modifier = Modifier)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .background(color = Purple80)
                                .aspectRatio(1f)
                                .padding(20.dp)
                        ) {
                            CountText(count = count, modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CountText(
        count: Int,
        modifier: Modifier
    ) {
        Text(
            text = "$count",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = modifier
        )
    }
}

@Composable
@Preview(showBackground = true)
fun InteractivePreview() {
    CameraScreen.Interactive(
        onSnap = {},
        onListImages = {},
        count = 5
    )
}
