package com.lernib.pagescanner.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lernib.pagescanner.ui.NavScreen
import com.lernib.pagescanner.ui.Navigation

sealed class SaveNavigation : Navigation {
    data class Camera(val props: CameraScreenProps) : SaveNavigation() {
        override fun toNavScreen(): NavScreen { return NavScreen.Camera(props) }
    }
}

data class SaveScreenProps(
    val scans: MutableList<Bitmap>
)

object SaveScreen {
    @Composable
    fun Screen(
        navigate: (SaveNavigation) -> Unit,
        props: SaveScreenProps
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Save as album"
                )
            }

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Get text"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                      navigate(
                          SaveNavigation.Camera(
                              props = CameraScreenProps(
                                  scans = props.scans
                              )
                          )
                      )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Back"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SaveScreenPreview() {
    SaveScreen.Screen(
        navigate = {},
        props = SaveScreenProps(
            scans = mutableListOf()
        )
    )
}
