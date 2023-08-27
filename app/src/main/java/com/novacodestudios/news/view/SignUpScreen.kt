package com.novacodestudios.recall.presentation.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.novacodestudios.news.util.Function.isNotNull
import com.novacodestudios.news.util.Screen
import com.novacodestudios.news.viewmodel.sign_up.SignUpEvent
import com.novacodestudios.news.viewmodel.sign_up.SignUpViewModel


import com.novacodestudios.recall.presentation.util.StandardButton
import com.novacodestudios.recall.presentation.util.StandardDivider
import com.novacodestudios.recall.presentation.util.StandardLinkedText
import com.novacodestudios.recall.presentation.util.StandardPasswordField
import com.novacodestudios.recall.presentation.util.StandardTextField
import kotlinx.coroutines.flow.collectLatest



@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {event->
            when (event) {
                is SignUpViewModel.UIEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
               is SignUpViewModel.UIEvent.SignUp -> {
                   navController.navigate(Screen.NewsFeedScreen.route){
                       popUpTo(Screen.NewsFeedScreen.route){
                           inclusive=true
                       }
                   }
               }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState)}
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)

            ) {
                val standardModifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                StandardTextField(
                    hint = "Adınız",
                    modifier = standardModifier,
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Adınız"
                        )
                    },
                    supportingText = state.nameError,
                    isError = state.nameError.isNotNull(),
                    text = state.name,
                    onValueChange = { viewModel.onEvent(SignUpEvent.NameChanged(it)) }
                )
                StandardTextField(
                    hint = "Soyadınız",
                    modifier = standardModifier,
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Soyadınız"
                        )
                    },
                    supportingText = state.surnameError,
                    isError = state.surnameError.isNotNull(),
                    text = state.surname,
                    onValueChange = { viewModel.onEvent(SignUpEvent.SurnameChanged(it)) }
                )

                StandardTextField(
                    hint = "E-mail",
                    modifier = standardModifier,
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "E-mail"
                        )
                    },
                    keyboardType = KeyboardType.Email,
                    supportingText = state.emailError,
                    isError = state.emailError.isNotNull(),
                    text = state.email,
                    onValueChange = { viewModel.onEvent(SignUpEvent.EmailChanged(it)) }
                )

                StandardPasswordField(
                    hint = "Şifre",
                    modifier = standardModifier,
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Şifre"
                        )
                    },
                    supportingText = state.passwordError,
                    isError = state.passwordError.isNotNull(),
                    text = state.password,
                    onValueChange = { viewModel.onEvent(SignUpEvent.PasswordChanged(it)) }
                )
                StandardPasswordField(
                    hint = "Şifre tekrar",
                    modifier = standardModifier,
                    iconStart = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Şifre tekrar"
                        )
                    },
                    supportingText = state.repeatedPasswordError,
                    isError = state.repeatedPasswordError.isNotNull(),
                    text = state.repeatedPassword,
                    onValueChange = { viewModel.onEvent(SignUpEvent.RepeatedPasswordChanged(it)) }
                )


                StandardButton(
                    onClick = { viewModel.onEvent(SignUpEvent.SignUp) },
                    text = "Kaydol",
                    modifier = standardModifier
                )

                StandardLinkedText(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(end = 8.dp, bottom = 8.dp),
                    text = "Zaten üyemisiniz? Giriş yapın",
                    onClick = {
                        navController.navigate(Screen.SignInScreen.route){
                            popUpTo(Screen.SignInScreen.route){
                                inclusive=true
                            }
                        }
                    })

                StandardDivider(text = "yada")
            }
        }
    }

}