package com.shawn.mvvmslideproject.util.widget

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomAlertDialog(
    title: String,
    text: String,
    confirmText: String,
    dismissText: String,
    onDismissRequest: () -> Unit,
    confirmClick: () -> Unit,
    dismissClick: () -> Unit
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = text) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = confirmClick) {
                Text(confirmText)
            }
        },

        dismissButton = {
            if (dismissText.isNotEmpty()) {
                Button(onClick = dismissClick) {
                    Text(text = dismissText)
                }
            }
        }

    )
}

@Preview
@Composable
fun showTest() {
    CustomAlertDialogWithTextField(
        "123",
        "456",
        "",
        "000",
        "111",
        {},
        {},
        {},
        {}
    )
}

@Composable
fun CustomAlertDialogWithTextField(
    title: String,
    text: String,
    value: String,
    confirmText: String,
    dismissText: String,
    onDismissRequest: () -> Unit,
    confirmClick: () -> Unit,
    dismissClick: () -> Unit,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType  = KeyboardType.Text
) {
    AlertDialog(
        title = { Text(text = title) },
        text = {
            OutlinedTextField(
                label = { Text(text = text) },
                singleLine = true,
                value = value,
                onValueChange = onValueChange
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = confirmClick) {
                Text(confirmText)
            }
        },

        dismissButton = {
            if (dismissText.isNotEmpty()) {
                Button(onClick = dismissClick) {
                    Text(text = dismissText)
                }
            }
        }
    )
}