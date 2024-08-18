package com.shawn.mvvmslideproject.model.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shawn.mvvmslideproject.ui.activitys.Greeting
import com.shawn.mvvmslideproject.ui.home.HomeList
import com.shawn.mvvmslideproject.ui.login.LoginScreen
import com.shawn.mvvmslideproject.ui.profile.ProfileGuestScreen
import com.shawn.mvvmslideproject.ui.profile.ProfileMemberScreen

@Composable
fun NavigationConfigurations(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(BottomNavItem.Home.route) {
            HomeList()
        }
        composable(BottomNavItem.Search.route) {
            ProfileMemberScreen()
        }
        composable(BottomNavItem.Profile.route) {
            LoginScreen()
        }
    }
}