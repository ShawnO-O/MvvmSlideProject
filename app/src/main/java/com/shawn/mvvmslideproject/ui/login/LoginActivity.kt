package com.shawn.mvvmslideproject.ui.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.ui.base.BaseActivity
import com.shawn.mvvmslideproject.ui.theme.BASIC_Slide_ProjectTheme
import com.shawn.mvvmslideproject.util.ShowToastLong
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BASIC_Slide_ProjectTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    val account by loginViewModel.account.collectAsState(initial = "")
    val password by loginViewModel.password.collectAsState(initial = "")
    val context = LocalContext.current


    LaunchedEffect(key1 = loginViewModel.toastSharedFlow) {
        loginViewModel.apply {
            toastSharedFlow.collect { message ->
                ShowToastLong(context, message)
                loginViewModel.clearToastMessage()
            }
        }
    }
    LaunchedEffect(key1 = loginViewModel.finishSharedFlow) {
        loginViewModel.apply {
            finishSharedFlow.collect { it ->
                (context as LoginActivity).finish()
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            AccountAndPassword(
                account,
                password,
                onAccountChange = {
                    loginViewModel.changeAccount(it)
                },
                onPasswordChange = {
                    loginViewModel.changePassword(it)
                },
                onLoginClick = {
                    loginViewModel.login(account, password)
                },
                onRegisterClick = {
                    loginViewModel.register(account, password)
                },
                paddingValues
            )
    }
}

//@Preview(showBackground = true)
@Composable
fun AccountAndPassword(
    account: String = "",
    password: String = "",
    onAccountChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    paddingValues: PaddingValues,
) {

    Column(modifier = Modifier.padding(paddingValues)) {
        OutlinedTextField(
            value = account,
            onValueChange = onAccountChange,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            singleLine = true,
            label = { Text(stringResource(id = R.string.login_account)) }
        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
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
                onClick = onRegisterClick
            ) {
                Text(text = stringResource(id = R.string.register))
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp),
                onClick = onLoginClick
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}