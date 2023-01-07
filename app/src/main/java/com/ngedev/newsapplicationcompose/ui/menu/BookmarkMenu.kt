package com.ngedev.newsapplicationcompose.ui.menu

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun BookmarkMenu(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    expanded: Boolean,
    onDismiss: () -> Unit
) {

    DropdownMenu(expanded = expanded, onDismissRequest = {
        onDismiss()
    }) {
        DropdownMenuItem(onClick = onMenuClick) {
            Text("Hapus")
        }
    }
}