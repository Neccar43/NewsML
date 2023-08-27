package com.novacodestudios.news.viewmodel.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novacodestudios.news.service.NewsAPI
import com.novacodestudios.news.util.Function.isNotNull
import com.novacodestudios.news.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val api: NewsAPI
) : ViewModel() {

    var state by mutableStateOf(SignUpState())

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> state = state.copy(email = event.email)
            is SignUpEvent.NameChanged -> state = state.copy(name = event.name)
            is SignUpEvent.PasswordChanged -> state = state.copy(password = event.password)
            is SignUpEvent.RepeatedPasswordChanged -> state =
                state.copy(repeatedPassword = event.repeatedPassword)

            is SignUpEvent.SignUp -> signUp()
            is SignUpEvent.SurnameChanged -> state = state.copy(surname = event.surname)
        }
    }

    private fun signUp() {
        val nameResult = validateName(state.name)
        val surnameResult = validateSurname(state.surname)
        val emailResult = validateEmail(state.email)
        val passwordResult = validatePassword(state.password)
        val repeatedPasswordResult =
            validateRepeatedPassword(state.password, state.repeatedPassword)

        val hasError = listOf(
            nameResult,
            surnameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { it.data != true }

        if (hasError) {
            state = state.copy(
                nameError = nameResult.message,
                surnameError = surnameResult.message,
                emailError = emailResult.message,
                passwordError = passwordResult.message,
                repeatedPasswordError = repeatedPasswordResult.message
            )
            return
        }
        viewModelScope.launch {
            try {
                val response = api.signUpUser(state.email, state.password)
                if (response.isSuccessful && response.body().isNotNull()) {
                    _eventFlow.emit(UIEvent.SignUp)
                }

            } catch (e: Exception) {
                _eventFlow.emit(UIEvent.ShowSnackbar(e.localizedMessage!!))
            }
        }

    }

    private fun validateSurname(surname: String): Resource<Boolean> {
        if (surname.isBlank()) {
            return Resource.Error(message = "Soyad boş olamaz.")
        }
        return Resource.Success(true)
    }

    private fun validateName(name: String): Resource<Boolean> {
        if (name.isBlank()) {
            return Resource.Error(message = "Ad boş olamaz.")
        }
        return Resource.Success(true)
    }

    private fun validateRepeatedPassword(
        password: String,
        repeatedPassword: String
    ): Resource<Boolean> {
        if (repeatedPassword.isBlank()) {
            return Resource.Error("Şifre tekrarı boş olamaz")
        }
        val isSame = password == repeatedPassword
        if (!isSame) {
            return Resource.Error("Şifreler eşleşmiyor")
        }
        return Resource.Success(true)
    }

    private fun validatePassword(password: String): Resource<Boolean> {
        if (password.isBlank()) {
            return Resource.Error("Şifre boş olamaz")
        }
        return Resource.Success(true)
    }

    private fun validateEmail(email: String): Resource<Boolean> {
        if (email.isBlank()) {
            return Resource.Error("email boş olamaz")
        }
        return Resource.Success(true)
    }


    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        data object SignUp : UIEvent()
    }

}

