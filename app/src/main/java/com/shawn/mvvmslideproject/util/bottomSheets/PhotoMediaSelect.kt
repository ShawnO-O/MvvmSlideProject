package com.shawn.mvvmslideproject.util.bottomSheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shawn.mvvmslideproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoMediaSelectBottomSheet(onDismiss: () -> Unit,onCameraClick:()->Unit,onAlbumClick:()->Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(start = 16.dp, end = 16.dp)
                .clickable {
                    onCameraClick()
                    onDismiss()
                }) {
                Icon(
                    painterResource(id = R.drawable.icon_camera), contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp),
                    text = stringResource(id = R.string.select_media_camera),
                    fontSize = 18.sp
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable {
                    onAlbumClick()
                    onDismiss()
                }) {
                Icon(
                    painterResource(id = R.drawable.icon_album), contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 16.dp),
                    text = stringResource(id = R.string.select_media_album),
                    fontSize = 18.sp
                )
            }
        }

    }
}