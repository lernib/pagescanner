package com.lernib.pagescanner.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lernib.pagescanner.ui.NavScreen
import com.lernib.pagescanner.ui.Navigation

enum class HomeNavigation : Navigation {
    CAMERA;

    override fun toNavScreen(): NavScreen {
        return when(this) {
            CAMERA -> NavScreen.Camera(
                scans = mutableListOf()
            )
        }
    }
}

data class HomeScreenProps(
    val onNavigate: (HomeNavigation) -> Unit
)

@Composable
fun HomeScreen(props: HomeScreenProps) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(40.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Box {
                SmallFloatingActionButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .size(65.dp)
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        "Add scan"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {  Text("Scan") },
                        onClick = {
                            expanded = false
                            props.onNavigate.invoke(HomeNavigation.CAMERA)
                        }
                    )
                }
            }
        }
    }
}
