package com.shawn.mvvmslideproject.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.navigation.BottomNavItem
import com.shawn.mvvmslideproject.navigation.NavigationConfigurations
import com.shawn.mvvmslideproject.ui.base.BaseActivity
import com.shawn.mvvmslideproject.ui.theme.BASIC_Slide_ProjectTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BASIC_Slide_ProjectTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavigationItems =
        listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Profile)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        bottomBar = {
            BottomNavigationBar(navController = navController, items = bottomNavigationItems)
        },
    ) { innerPadding ->
        NavigationConfigurations(navController, innerPadding)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomNavItem>) {
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_300)
    ) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(text = screen.label) },
                alwaysShowLabel = false,
            )

        }
    }
}
