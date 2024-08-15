package com.shawn.mvvmslideproject.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.util.ShowToastLong


@Composable
fun LoginScreen() {
    Scaffold { paddingValues ->
        AccountAndPassword(paddingValues)
    }
}

//@Preview(showBackground = true)
@Composable
fun AccountAndPassword(
    paddingValues: PaddingValues,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    var account by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    LaunchedEffect(key1 = loginViewModel.toastSharedFlow) {
        loginViewModel.toastSharedFlow.collect { message ->
            ShowToastLong(context, message)
            loginViewModel.clearToastMessage()
        }
    }

    Column(modifier = Modifier.padding(paddingValues)) {
        OutlinedTextField(
            value = account,
            onValueChange = { account = it },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            singleLine = true,
            label = { Text(stringResource(id = R.string.login_account)) }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            singleLine = true,
            label = { Text(stringResource(id = R.string.login_password)) }
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp),

                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(id = R.string.register))
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp),
                onClick = {
                    loginViewModel.login(account, password)
                }
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}