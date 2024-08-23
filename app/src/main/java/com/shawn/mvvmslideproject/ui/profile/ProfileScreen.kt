package com.shawn.mvvmslideproject.ui.profile

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.model.room.profile.ProfileInfo
import com.shawn.mvvmslideproject.ui.login.LoginActivity
import com.shawn.mvvmslideproject.util.ShowToastLong


//搭配登入、註冊使用
//使用room實作


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val hasMemberId by profileViewModel.hasMemberId.collectAsState(initial = false)
    val profileInfo by profileViewModel.profileInfo.collectAsState()

    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditNameDialog by remember { mutableStateOf(false) }
    var showEditEmailDialog by remember { mutableStateOf(false) }
    val loginLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
            Log.d("shawnTest", "result is ok")
            profileViewModel.hasMemberId()

//            }
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
    profileViewModel.getProfileData()

    if (hasMemberId) {
//        profileViewModel.getProfileData()
        ProfileMemberScreen(
            profileInfo,
            hasMemberId,
            logoutClick = {
                showLogoutDialog = true
            },
            nameEditClick = {
                showEditNameDialog = true
            },
            birthEditClick = {

            },
            emailEditClick = {
                showEditEmailDialog = true
            },
            onGenderChange = {
                profileViewModel.saveGender(it)
            }

        )
    } else {
        ProfileGuestScreen(onLoginClick = {
            val intent = Intent(context, LoginActivity::class.java)
            loginLauncher.launch(intent)
        })
    }



    InitLogoutDialog(
        showLogoutDialog,
        conFirmClick = {
            profileViewModel.logout()
            showLogoutDialog = false
        },
        closeDialog = {
            showLogoutDialog = false
        })

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
        })

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

//@Preview(showBackground = true)
@Composable
fun ProfileMemberScreen(
    profileInfo: ProfileInfo,
    hasMemberId: Boolean,
    logoutClick: () -> Unit = {},
    nameEditClick: () -> Unit = {},
    birthEditClick: () -> Unit = {},
    emailEditClick: () -> Unit = {},
    onGenderChange: (String) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize(1f)) {
        if (hasMemberId)
            Text(
                text = "登出",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .clickable(onClick = logoutClick)
            )
        Column(modifier = Modifier.align(Alignment.Center)) {
            AsyncImage(
                //both way was worked.It's depends on the setting.
//              model = "https://www.pili.com.tw/img/role/640x741/su_1.jpg?v=1711533444",
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .data("https://www.pili.com.tw/img/role/640x741/su_1.jpg?v=1711533444")
                    .memoryCachePolicy(CachePolicy.ENABLED).build(),
                contentDescription = "",
                placeholder = painterResource(id = R.drawable.img_head_shot),
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = profileInfo.name ?: "name".ifEmpty { "name" }, fontSize = 20.sp)
                IconButton(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .align(Alignment.CenterVertically),
                    onClick = nameEditClick
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "")
                }
            }


            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                ProfileGenderGroup(
                    profileInfo.gender ?: "",
                    onGenderChange = { onGenderChange(it) })
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = profileInfo.birthDay ?: "".ifEmpty { "birth" }, fontSize = 20.sp)
                IconButton(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .align(Alignment.CenterVertically),
                    onClick = birthEditClick
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "")
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = profileInfo.email ?: "".ifEmpty { "email" }, fontSize = 20.sp)
                IconButton(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .align(Alignment.CenterVertically),
                    onClick = emailEditClick
                ) {
                    Icon(
                        Icons.Default.Edit, contentDescription = "",

                        )
                }
            }
        }
    }
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
                Text(text = "請先登入")
            }
        }
    }
}