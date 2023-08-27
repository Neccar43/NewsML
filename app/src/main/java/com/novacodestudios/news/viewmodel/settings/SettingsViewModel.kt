package com.novacodestudios.news.viewmodel.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.news.service.NewsAPI
import com.novacodestudios.recall.presentation.settings.SettingsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val api: NewsAPI
) : ViewModel() {
    var state by mutableStateOf(SettingsState())
    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.DeleteAccount -> TODO()
            is SettingsEvent.SignOut -> {signOut()}
            is SettingsEvent.ThemeChanged -> TODO()
        }
    }

    private var job: Job? = null


    private fun deleteAccount() {
        // TODO: Burayı sonradan ekle
    }

    private fun signOut() {
        // TODO: Success dönene kadar beklet
        viewModelScope.launch {
            //api.signOutUser(0)
        }
    }

}