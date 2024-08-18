package com.shawn.mvvmslideproject.ui.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.shawn.mvvmslideproject.R


//搭配登入、註冊使用
//使用room實作

@Composable
fun ProfileScreen() {

}

@Preview(showBackground = true)
@Composable
fun ProfileMemberScreen() {
    Box(modifier = Modifier.fillMaxSize(1f)) {
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
                Text(text = "name", fontSize = 20.sp)
                IconButton(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        Log.d("shawnTest", "click")
                    }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "")
                }
            }


            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp)) {
                ProfileGenderGroup()
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "birth", fontSize = 20.sp)
                IconButton(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        Log.d("shawnTest", "click2")
                    }
                ) {
                    Icon(
                        Icons.Default.Edit, contentDescription = "",

                        )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "email", fontSize = 20.sp)
                IconButton(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .align(Alignment.CenterVertically),
                    onClick = {
                        Log.d("shawnTest", "click2")
                    }
                ) {
                    Icon(
                        Icons.Default.Edit, contentDescription = "",

                        )
                }
            }
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OutlinedButton(
                    modifier = Modifier.padding(15.dp),
                    onClick = { /*TODO*/ }) {
                    Text("取消")
                }
                OutlinedButton(
                    modifier = Modifier.padding(15.dp),
                    onClick = { /*TODO*/ }) {
                    Text("儲存", color = Color.Blue)
                }

            }
        }
    }
}


@Composable
fun ProfileGenderGroup() {
    val options = listOf("男", "女", "隱藏")
    val selectedOption = remember { mutableStateOf(options[0]) }

    Row {
        options.forEach { option ->
            Row {
                RadioButton(
                    selected = selectedOption.value == option,
                    onClick = { selectedOption.value = option }
                )
                Text(option, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}


@Composable
fun ProfileGuestScreen() {
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
                onClick = { /*TODO*/ }) {
                Text(text = "請先登入")
            }
        }
    }
}