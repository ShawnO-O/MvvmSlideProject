package com.shawn.mvvmslideproject.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeList(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    homeViewModel.getAttractions(1)
    val data = homeViewModel.stateFlow.collectAsState()
    Scaffold { innerPadding ->
        Text(text = data.value, modifier = Modifier.padding(innerPadding))

    }
}