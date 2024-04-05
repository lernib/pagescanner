package com.lernib.pagescanner.ui.screen

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import com.lernib.pagescanner.ui.NavScreen
import com.lernib.pagescanner.ui.Navigation

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

@Composable
fun ListImagesScreen(
    onNavigate: (ListImagesNavigation) -> Unit,
    props: ListImagesScreenProps
) {
    // TODO
}
