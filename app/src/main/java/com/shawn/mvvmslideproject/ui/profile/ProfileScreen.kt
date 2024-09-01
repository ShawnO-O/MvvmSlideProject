@file:OptIn(ExperimentalMaterial3Api::class)

package com.shawn.mvvmslideproject.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo
import com.shawn.mvvmslideproject.ui.login.LoginActivity
import com.shawn.mvvmslideproject.util.OnlyGetPermission
import com.shawn.mvvmslideproject.util.ShowToastLong
import com.shawn.mvvmslideproject.util.createImageFile
import com.shawn.mvvmslideproject.util.extensions.cellTextColor
import com.shawn.mvvmslideproject.util.extensions.isCellEmpty
import com.shawn.mvvmslideproject.util.useCamera2
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Objects


//搭配登入、註冊使用
//使用room實作


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val hasMemberId by profileViewModel.hasMemberId.collectAsState(initial = false)
    val profileInfo by profileViewModel.profileInfo.collectAsState()
    val context = LocalContext.current

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    val loginLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                profileViewModel.hasMemberId()
            }
        }



    LaunchedEffect(key1 = profileViewModel.toastSharedFlow) {
        profileViewModel.apply {
            toastSharedFlow.collect { message ->
                ShowToastLong(context, message)
                profileViewModel.clearToastMessage()
            }
        }
    }
//    LaunchedEffect(key1 = Unit) {
//
//    }
    profileViewModel.hasMemberId()
    if (hasMemberId) {
        profileViewModel.getProfileData()
        ProfileMemberScreen(
            context,
            profileInfo,
            profileViewModel
        )
    } else {
        ProfileGuestScreen(onLoginClick = {
            val intent = Intent(context, LoginActivity::class.java)
            loginLauncher.launch(intent)
        })
    }


    var timeStamp: Long? = 0L
    if (profileInfo.birthDay?.isNotEmpty() == true) {
        val localDate = LocalDate.parse(profileInfo.birthDay, dateFormatter)
        timeStamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
    LaunchedEffect(key1 = timeStamp) {
        datePickerState.selectedDateMillis = timeStamp
    }
}


@Composable
fun ProfileMemberScreen(
    context: Context,
    profileInfo: ProfileInfo,
    profileViewModel: ProfileViewModel
) {
    Column(
//        modifier = Modifier.fillMaxSize(1f),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            LogoutCell(profileViewModel)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                HeadShotCell(context, profileInfo, profileViewModel)
                NameCell(profileInfo, profileViewModel)
                GenderCell(profileInfo, profileViewModel)
                BirthDayCell(profileInfo, profileViewModel)
                EmailCell(profileInfo, profileViewModel)
            }
        }
    }
}

@Composable
fun LogoutCell(profileViewModel: ProfileViewModel) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Text(
        text = "登出",
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                showLogoutDialog = true
            }
    )

    InitLogoutDialog(
        showLogoutDialog,
        conFirmClick = {
            profileViewModel.logout()
            showLogoutDialog = false
        },
        closeDialog = {
            showLogoutDialog = false
        }
    )
}

@Composable
fun HeadShotCell(context: Context, profileInfo: ProfileInfo, profileViewModel: ProfileViewModel) {
    var showRationale by remember { mutableStateOf(false) }
    var photo by remember { mutableStateOf(profileInfo.headerShot) }
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "${context.applicationContext.packageName}.provider",
        file
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            photo = uri.toString()
        }
    )
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            //both way was worked.It's depends on the setting.
//              model = "https://www.pili.com.tw/img/role/640x741/su_1.jpg?v=1711533444",
            model = ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .data(photo)
                .memoryCachePolicy(CachePolicy.ENABLED).build(),
            contentDescription = "",
            placeholder = painterResource(id = R.drawable.img_head_shot),
            modifier = Modifier
                .clickable {
                    useCamera2(context, uri, cameraLauncher) {
                        showRationale = true
                    }
                }
                .clip(CircleShape)
//            .align(Alignment.CenterHorizontally)
        )

        OnlyGetPermission(showRationale,
            onShowDialog = {
                showRationale = true
            },
            onGranted = {
                useCamera2(context, uri, cameraLauncher) {
                    showRationale = true
                }
            },
            onDismiss = {
                showRationale = false
            })
    }
}

@Composable
fun NameCell(profileInfo: ProfileInfo, profileViewModel: ProfileViewModel) {
    var showEditNameDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(top = 15.dp)
        ) {
            Text(
                text = profileInfo.name.isCellEmpty(stringResource(id = R.string.profile_name)),
                color = colorResource(id = profileInfo.name.cellTextColor()),
                fontSize = 20.sp
            )
            IconButton(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(16.dp)
                    .height(16.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    showEditNameDialog = true
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "")
            }
        }
    }
    InitEditDialog(
        showDialog = showEditNameDialog,
        title = "編輯姓名",
        value = profileInfo.name ?: "",
        confirmClick = {
            profileViewModel.saveName(it)
            showEditNameDialog = false
        },
        closeDialog = {
            showEditNameDialog = false
        }
    )
}

@Composable
fun EmailCell(profileInfo: ProfileInfo, profileViewModel: ProfileViewModel) {
    var showEditEmailDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(top = 15.dp)
        ) {
            Text(
                text = profileInfo.email.isCellEmpty(stringResource(id = R.string.profile_email)),
                color = colorResource(id = profileInfo.name.cellTextColor()),
                fontSize = 20.sp
            )
            IconButton(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(16.dp)
                    .height(16.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    showEditEmailDialog = true
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "")
            }
        }
    }
    InitEditDialog(
        showDialog = showEditEmailDialog,
        title = "編輯信箱",
        value = profileInfo.email ?: "",
        confirmClick = {
            profileViewModel.saveEmail(it)
            showEditEmailDialog = false
        },
        closeDialog = {
            showEditEmailDialog = false
        },
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun GenderCell(profileInfo: ProfileInfo, profileViewModel: ProfileViewModel) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(top = 15.dp)
        ) {
            ProfileGenderGroup(
                profileInfo.gender ?: "",
                onGenderChange = { profileViewModel.saveGender(it) })
        }
    }
}

@Composable
fun BirthDayCell(
    profileInfo: ProfileInfo,
    profileViewModel: ProfileViewModel,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(top = 15.dp)
        ) {
            Text(
                text = profileInfo.birthDay.isCellEmpty(stringResource(id = R.string.profile_birth)),
                color = colorResource(id = profileInfo.name.cellTextColor()),
                fontSize = 20.sp
            )
            IconButton(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .width(16.dp)
                    .height(16.dp)
                    .align(Alignment.CenterVertically),
                onClick = {
                    showDatePicker = true
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = "")
            }
        }
    }

    if (showDatePicker) {
        CustomDatePickerDialog(state = datePickerState,
            onDismissRequest = {
                showDatePicker = false
            },
            onConfirmButtonClicked = {
                if (it != null) {
                    val selectDate = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.of("UTC+8"))
                        .toLocalDate()
                        .format(dateFormatter)
                    profileViewModel.saveBirth(selectDate)
                }
                showDatePicker = false
            }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    state: DatePickerState,
    confirmButtonText: String = "OK",
    dismissButtonText: String = "Cancel",
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: (Long?) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = { onConfirmButtonClicked(state.selectedDateMillis) }
            ) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = dismissButtonText)
            }
        },
        content = {
            DatePicker(
                state = state,
                showModeToggle = false,
                headline = null,
                title = null
            )
        }
    )
}

@Composable
fun ProfileGenderGroup(value: String, onGenderChange: (String) -> Unit = {}) {
    val options = listOf("男", "女", "隱藏")
    val selectedOption = remember { mutableStateOf(value) }

    Row {
        options.forEach { option ->
            Row {
                RadioButton(
                    selected = selectedOption.value == option,
                    onClick = {
                        selectedOption.value = option
                        onGenderChange(option)
                    }
                )
                Text(option, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}


@Composable
fun ProfileGuestScreen(onLoginClick: () -> Unit = {}) {

    Box(modifier = Modifier.fillMaxSize(1f)) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_head_shot),
                contentDescription = ""
            )
            OutlinedButton(
                modifier = Modifier.padding(top = 20.dp),
                onClick = onLoginClick
            ) {
                Text(text = stringResource(id = R.string.login_first))
            }
        }
    }
}