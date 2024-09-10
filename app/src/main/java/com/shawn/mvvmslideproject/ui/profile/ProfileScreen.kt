@file:OptIn(ExperimentalMaterial3Api::class)

package com.shawn.mvvmslideproject.ui.profile

import android.Manifest
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import com.shawn.mvvmslideproject.BuildConfig
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.extensions.cellTextColor
import com.shawn.mvvmslideproject.extensions.isCellEmpty
import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo
import com.shawn.mvvmslideproject.ui.login.LoginActivity
import com.shawn.mvvmslideproject.util.ShowToastLong
import com.shawn.mvvmslideproject.util.bottomSheets.PhotoMediaSelectBottomSheet
import com.shawn.mvvmslideproject.util.createImageFile
import com.shawn.mvvmslideproject.util.useAlbum
import com.shawn.mvvmslideproject.util.useCamera
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Objects


//搭配登入、註冊使用
//使用room實作

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val hasMemberId by profileViewModel.hasMemberId.collectAsState(initial = false)
    val profileInfo by profileViewModel.profileInfo.collectAsState()

    val loginLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            profileViewModel.hasMemberId()
        }

    LaunchedEffect(key1 = profileViewModel.toastSharedFlow) {
        profileViewModel.apply {
            toastSharedFlow.collect { message ->
                ShowToastLong(context, message)
                profileViewModel.clearToastMessage()
            }
        }
    }

    profileViewModel.hasMemberId()
    if (hasMemberId) {
        profileViewModel.getProfileData()
        ProfileMemberScreen(
            profileInfo,
            profileViewModel
        )
    } else {
        ProfileGuestScreen(onLoginClick = {
            val intent = Intent(context, LoginActivity::class.java)
            loginLauncher.launch(intent)
        })
    }
}

fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): Bitmap? {
    return contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }
}

@Composable
fun ProfileMemberScreen(
    profileInfo: ProfileInfo,
    profileViewModel: ProfileViewModel
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                HeadshotCell(profileInfo, profileViewModel)
                NameCell(profileInfo, profileViewModel)
                GenderCell(profileInfo, profileViewModel)
                BirthDayCell(profileInfo, profileViewModel)
                EmailCell(profileInfo, profileViewModel)
            }
        }
    }
}

@Composable
fun HeadshotCell(profileInfo: ProfileInfo, profileViewModel: ProfileViewModel) {

    val context = LocalContext.current
    val file = remember { context.createImageFile() }
    val uri = remember {
        FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
        )
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showCameraRationale by remember { mutableStateOf(false) }
    var showAlbumRationale by remember { mutableStateOf(false) }
    var capturedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { Success ->
            if (Success) {
                capturedImageUri = uri
                profileViewModel.saveHeadshot(capturedImageUri)
            }
        }
    val albumLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { backUri: Uri? ->
        if (backUri != null) {
            capturedImageUri = backUri
            profileViewModel.saveHeadshot(backUri)
            //Here sould be upload after success.
        }
    }
    val cameraRequestLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showBottomSheet = true
            } else {
                showCameraRationale = true
            }
        }

    val selectMediaRequestLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions())
        { permission: Map<String, Boolean> ->
            val readGranted =
                permission[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
            val writeGranted =
                permission[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: false
            if (readGranted && writeGranted) {
                showBottomSheet = true
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    albumLauncher.launch("image/*")
                } else {
                    showAlbumRationale = true
                }
            }
        }

    LaunchedEffect(capturedImageUri) {
        // 這裡可以執行一些操作，例如重新設定 profileInfo.headerShot 的值
        profileViewModel.saveHeadshot(capturedImageUri)
    }
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            //both way was worked.It's depends on the setting.
//              model = "https://www.pili.com.tw/img/role/640x741/su_1.jpg?v=1711533444",
            model = ImageRequest.Builder(LocalContext.current)
                .crossfade(true)
                .data(profileInfo.headerShot)
                .memoryCachePolicy(CachePolicy.ENABLED).build(),
            error = painterResource(id = R.drawable.img_head_shot),
            contentDescription = "",
            placeholder = painterResource(id = R.drawable.img_head_shot),
            modifier = Modifier
                .padding(top = 20.dp)
                .size(300.dp, 300.dp)
                .clickable {
                    showBottomSheet = true
                }
                .clip(CircleShape)
        )
    }

    if (showBottomSheet) {
        PhotoMediaSelectBottomSheet(
            onDismiss = { showBottomSheet = false },
            onCameraClick = {
                useCamera(
                    onGetPermission = {
                        cameraRequestLauncher.launch(Manifest.permission.CAMERA)
                    },
                    onCamera = {
                        cameraLauncher.launch(uri)
                    }
                )
            },
            onAlbumClick = {
                useAlbum(albumLauncher) {
                    selectMediaRequestLauncher.launch(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                }
            }
        )
    }

    ShowRationale(
        "需要相機權限",
        "此應用程式需要相機權限才能拍照。",
        showCameraRationale,
        onConfirm = {
            cameraRequestLauncher.launch(
                Manifest.permission.CAMERA
            )
        },
        onDismiss = { showCameraRationale = false })
    ShowRationale(
        "需要相簿權限",
        "此應用程式需要相簿權限才能選擇照片。",
        showAlbumRationale,
        onConfirm = {
            selectMediaRequestLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        },
        onDismiss = { showAlbumRationale = false })
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
fun ShowRationale(
    title: String,
    text: String,
    showRationale: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showRationale) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = { Text(text) },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("授予權限")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("取消")
                }
            }
        )
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


    var timeStamp: Long? = 0L
    if (profileInfo.birthDay?.isNotEmpty() == true) {
        val localDate = LocalDate.parse(profileInfo.birthDay, dateFormatter)
        timeStamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
    LaunchedEffect(key1 = timeStamp) {
        datePickerState.selectedDateMillis = timeStamp
    }

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

