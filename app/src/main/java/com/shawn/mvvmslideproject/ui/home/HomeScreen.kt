package com.shawn.mvvmslideproject.ui.home

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.model.data.home.Facilities
import com.shawn.mvvmslideproject.model.data.home.Images
import com.shawn.mvvmslideproject.model.data.home.Info
import com.shawn.mvvmslideproject.ui.tool.BigPhotoViewerActivity

@Composable
fun HomeList(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    homeViewModel.getAttractionsFirst()
    val attractions = homeViewModel.attractions.collectAsState()
    Scaffold { innerPadding ->
        ContentList(
            attractions.value,
            innerPadding,
            loadMoreListener = { homeViewModel.getAttractionsMore() })
    }
}

@Composable
fun ContentList(
    contacts: MutableList<Info>,
    innerPadding: PaddingValues,
    loadMoreListener: () -> Unit = {}
) {
    val buffer = 3 // load more when scroll reaches last n item, where n >= 1
    val listState = rememberLazyListState()
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - buffer
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) loadMoreListener.invoke()
    }

    LazyColumn(state = listState, modifier = Modifier.padding(innerPadding)) {
        items(contacts) { contacts ->
            ListItem(contacts)
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListItem(contacts: Info) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(10.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.teal_300)),
    ) {
        Column {
            when (contacts.images) {
                is Images.ImageData -> {
                    Box(modifier = Modifier.clickable {
                        val intent = Intent(context, BigPhotoViewerActivity::class.java)
                        //將contacts.images的資料用Praseable的方式傳到另一個Activity
                        intent.putExtra("images", contacts.images)
                        context.startActivity(intent)
                    }) {
                        AsyncImageMaker(contacts.images.image.src ?: "")
                    }
                }

                is Images.ImagesData -> {
                    LazyRow {
                        items(contacts.images.images) { lists ->
                            Box(modifier = Modifier.clickable {
                                val intent = Intent(context, BigPhotoViewerActivity::class.java)
                                //將contacts.images的資料用Praseable的方式傳到另一個Activity
                                intent.putExtra("images", contacts.images)
                                context.startActivity(intent)
                            }) {
                                AsyncImageMaker(lists.src ?: "")
                            }
                        }
                    }
                }
                else -> {
                    //nothing to do
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(contacts.name ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(contacts.district ?: "", color = colorResource(id = R.color.teal_700))
                Text(contacts.description ?: "")
                FlowRow {
                    when (contacts.facilities) {
                        is Facilities.FacilitiesData -> {
                            contacts.facilities.facility.forEachIndexed { index, tag ->
                                TagTextView(index,tag)
                            }
                        }

                        is Facilities.FacilitiesString -> {
                            Text(contacts.facilities.value)
                        }

                        else -> {
                            //nothing to do
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview(){
    TagTextView(0,"123")
}

@Composable
fun TagTextView(index:Int,tag:String){
    Card(
        modifier = Modifier.padding(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purple_200),
            contentColor = colorResource(id = R.color.purple_600)
        ),
        border = BorderStroke(0.5.dp, colorResource(id = R.color.purple_300))
    ) {
        Text(
            text = tag,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                .layoutId(index)
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
        contentScale = ContentScale.Crop,
        contentDescription = "",
        placeholder = painterResource(id = R.drawable.loading2),
        modifier = Modifier
            .height(300.dp)
            .width(300.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}


@Preview
@Composable
fun ExpandableComponent() {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Button(onClick = { expanded = !expanded }) {
            Text(if (expanded) "縮小" else "展開")
        }
        AnimatedVisibility(visible = expanded) {
            Text(text = "這邊可以展開")
        }
    }
}