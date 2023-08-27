package com.novacodestudios.recall.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.novacodestudios.news.util.Screen
import com.novacodestudios.news.viewmodel.settings.SettingsViewModel
import com.novacodestudios.recall.presentation.util.StandardDialog
import com.novacodestudios.recall.presentation.util.StandardText
import kotlinx.coroutines.runBlocking

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = hiltViewModel()) {
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 10.dp),

            ) {
            val dividerModifier = Modifier.padding(horizontal = 8.dp)
            val textModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 15.dp)

            StandardText(
                text = "Hesabım",
                modifier = textModifier,
                imageVector = Icons.Outlined.Person,
                description = "Hesabım"
            )

            Divider(modifier = dividerModifier)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StandardText(
                    text = "Koyu tema",
                    imageVector = Icons.Outlined.DarkMode,
                    description = "Koyu tema"
                )

                Switch(
                    checked = state.isDarkTheme,
                    onCheckedChange = {
                        viewModel.onEvent(SettingsEvent.ThemeChanged(!state.isDarkTheme))
                    },
                    modifier = Modifier
                        .height(14.dp)
                        .padding(end = 20.dp)
                )
            }

            Divider(modifier = dividerModifier)

            StandardText(
                text = "Hesabı sil", modifier = textModifier,
                imageVector = Icons.Outlined.Delete,
                description = "Hesabı sil"
            )

            Divider(modifier = dividerModifier)
            var visible by remember {
                mutableStateOf(false)
            }
            StandardText(
                text = "Çıkış yap",
                modifier = textModifier.clickable { visible = true },
                imageVector = Icons.Outlined.Logout,
                description = "Çıkış yap",
            )

            Divider(modifier = dividerModifier)

            if (visible)
                StandardDialog(title = "Oturumu kapat",
                    onDismiss = { visible = false }, onRequest = {
                        // TODO: Buraya isLoading ekle
                        runBlocking {
                            viewModel.onEvent(SettingsEvent.SignOut)
                        }
                        navController.navigate(Screen.SignInScreen.route) {
                            popUpTo(Screen.SettingsScreen.route) {
                                inclusive = true
                            }
                        }
                        visible = false
                    }) {
                    Spacer(modifier = Modifier.height(10.dp))
                    StandardText(
                        text = "Oturumunuzu kapatmak istediğinizden emin misiniz?",
                        modifier = Modifier,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                }


        }
    }
}

