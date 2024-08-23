package com.shawn.mvvmslideproject.ui.tool

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.model.data.home.Image
import com.shawn.mvvmslideproject.model.data.home.Images
import com.shawn.mvvmslideproject.ui.base.BaseActivity

class BigPhotoViewerActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val images = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("images", Images::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("images") as? Images
        }

        setContent {
            Scaffold { innerPadding ->
                when (images) {
                    is Images.ImageData -> {
                        MainScreen(images.image, innerPadding)
                    }

                    is Images.ImagesData -> {
                        MainScreen(images.images, innerPadding)
                    }

                    else -> {
                        //nothing to do
                    }
                }

            }
        }
    }
}

@Composable
fun MainScreen(image: Image, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImageMaker(image.src ?: "")
            Text(
                fontSize = 18.sp,
                text = image.subject ?: "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )
            SubjectText(image.subject ?: "")
        }
    }
}

@Composable
fun MainScreen(
    images: List<Image>,
    innerPadding: PaddingValues,
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        LazyColumn {
            items(images) { image ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally)
                ) {
                    AsyncImageMaker(image.src ?: "")
                    SubjectText(image.subject ?: "")
                }
            }
        }

    }
}

@Composable
fun SubjectText(subject: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            fontSize = 18.sp,
            text = subject,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun AsyncImageMaker(src: String) {
    AsyncImage(
        //both way was worked.It's depends on the setting.
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(src)
            .memoryCachePolicy(CachePolicy.ENABLED).build(),
        contentScale = ContentScale.FillBounds,
        contentDescription = "",
        placeholder = painterResource(id = R.drawable.loading2),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 300.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.purple_200))
    )
}