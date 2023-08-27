package com.novacodestudios.news.viewmodel.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.news.service.NewsAPI
import com.novacodestudios.news.util.Const.UID
import com.novacodestudios.news.util.Function.isNotNull
import com.novacodestudios.news.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val api:NewsAPI
) : ViewModel() {
    var state by mutableStateOf(SignInState())

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> state = state.copy(email = event.email)
            is SignInEvent.PasswordChanged -> state = state.copy(password = event.password)
            is SignInEvent.SignIn -> signIn()
        }
    }

    private fun signIn() {

        val emailResult = validateEmail(state.email)
        val passwordResult = validatePassword(state.password)


        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { it.data != true }
        if (hasError) {
            state = state.copy(
                emailError = emailResult.message,
                passwordError = passwordResult.message,
            )
            return
        }
        viewModelScope.launch {
            try {
                val response=api.signInUser(state.email,state.password)
                if (response.isSuccessful&&response.isNotNull()){
                    val userResponse=api.getUserId(email = state.email)
                    if (userResponse.isSuccessful){
                        UID= userResponse.body()!!
                    }

                    _eventFlow.emit(UIEvent.SignIn)
                }
            } catch (e: Exception) {
                _eventFlow.emit(UIEvent.ShowSnackbar(e.localizedMessage!!))
            }
        }
    }



    sealed class UIEvent{
        data class ShowSnackbar(val message:String): UIEvent()
        data object SignIn: UIEvent()
    }

    private fun validatePassword(password: String): Resource<Boolean> {
        if (password.isBlank()){
            return Resource.Error("Şifre boş olamaz")
        }
        return Resource.Success(true)
    }

    private fun validateEmail(email: String): Resource<Boolean> {
        if (email.isBlank()){
            return Resource.Error("email boş olamaz")
        }
        return Resource.Success(true)
    }
}