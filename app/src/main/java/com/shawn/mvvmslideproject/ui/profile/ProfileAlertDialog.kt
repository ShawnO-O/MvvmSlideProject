package com.shawn.mvvmslideproject.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.shawn.mvvmslideproject.util.widget.CustomAlertDialog
import com.shawn.mvvmslideproject.util.widget.CustomAlertDialogWithTextField


@Composable
fun InitLogoutDialog(
    showDialog: Boolean,
    conFirmClick: () -> Unit = {},
    closeDialog: () -> Unit = {}
) {
    if (showDialog) {
        CustomAlertDialog(
            title = "你確定要登出嗎?",
            text = "登出後資料將完全清除",
            confirmText = "登出",
            dismissText = "取消",
            onDismissRequest = closeDialog,
            confirmClick = conFirmClick,
            dismissClick = closeDialog
        )
    }
}

@Composable
fun InitEditDialog(
    title: String,
    value: String ,
    confirmText: String = "儲存",
    dismissText: String = "取消",
    showDialog: Boolean,
    confirmClick: (String) -> Unit,
    closeDialog: () -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var _value by remember { mutableStateOf(value) }

    if (showDialog) {
        CustomAlertDialogWithTextField(
            title = title,
            text = "",
            value = _value,
            confirmText = confirmText,
            dismissText = dismissText,
            onDismissRequest = closeDialog,
            confirmClick = { confirmClick(_value) },
            dismissClick = closeDialog,
            onValueChange = {
                _value = it
            },
            keyboardType = keyboardType
        )
    }
}