package com.lernib.pagescanner.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Spacer(modifier = Modifier.weight(1f))
            EditButton()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EditButton() {
    SmallFloatingActionButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .size(65.dp)
    ) {
        Icon(
            Icons.Filled.Edit,
            "Add scan"
        )
    }
}
