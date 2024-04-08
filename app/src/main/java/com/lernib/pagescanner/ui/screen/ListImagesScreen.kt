package com.lernib.pagescanner.ui.screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.lernib.pagescanner.R
import com.lernib.pagescanner.ui.NavScreen
import com.lernib.pagescanner.ui.Navigation
import com.lernib.pagescanner.ui.utils.BitmapUtils

sealed class ListImagesNavigation : Navigation {
    data class Camera(val props: CameraScreenProps) : ListImagesNavigation()

    override fun toNavScreen(): NavScreen {
        return when(this) {
            is Camera -> NavScreen.Camera(props)
        }
    }
}

data class ListImagesScreenProps(
    val scans: MutableList<Bitmap>,
)

object ListImagesScreen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun Screen(
        onNavigate: (ListImagesNavigation) -> Unit,
        props: ListImagesScreenProps
    ) {
        val (index, setIndex) = remember {
            mutableIntStateOf(props.scans.size - 1)
        }

        Column {
            Box(modifier = Modifier.weight(1f)) {
                ScanImage(
                    props = ScanImageProps(
                        bitmap = props.scans.getOrNull(index)!!
                    )
                )
            }

            BottomBar(
                events = BottomBarEvents(
                    previous = {
                        if (index > 0) {
                            setIndex(index - 1)
                        }
                    },
                    next = {
                        if (index < props.scans.size - 1) {
                            setIndex(index + 1)
                        }
                    },
                    backToCamera = {
                        onNavigate.invoke(
                            ListImagesNavigation.Camera(
                                props = CameraScreenProps(
                                    scans = props.scans
                                )
                            )
                        )
                    }
                )
            )
        }
    }

    data class ScanImageProps(
        val bitmap: Bitmap
    )

    @Composable
    fun ScanImage(
        props: ScanImageProps
    ) {
        Image(
            bitmap = props.bitmap.asImageBitmap(),
            contentDescription = "Scan preview"
        )
    }

    data class BottomBarEvents(
        val previous: () -> Unit,
        val next: () -> Unit,
        val backToCamera: () -> Unit
    ) {
        companion object {
            val preview: BottomBarEvents
                get() = BottomBarEvents(
                    previous = {},
                    next = {},
                    backToCamera = {}
                )
        }
    }

    @Composable
    fun BottomBar(
        events: BottomBarEvents
    ) {
        BottomAppBar(
            actions = {
                IconButton(onClick = events.previous) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                }

                IconButton(onClick = events.next) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Go forward")
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = events.backToCamera) {
                    Icon(painterResource(R.drawable.photo_camera), contentDescription = "Back to camera")
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun BottomBarPreview() {
    ListImagesScreen.BottomBar(
        events = ListImagesScreen.BottomBarEvents.preview
    )
}

@Composable
@Preview(showBackground = true)
fun ScanImagePreview() {
    val context = LocalContext.current
    val bitmap = remember {
        BitmapUtils.getDefaultBitmap(context)
    }

    val props = ListImagesScreen.ScanImageProps(
        bitmap = bitmap
    )

    ListImagesScreen.ScanImage(props)
}
