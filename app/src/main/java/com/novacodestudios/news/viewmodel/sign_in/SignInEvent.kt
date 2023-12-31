package com.novacodestudios.news.viewmodel.sign_in

sealed class SignInEvent{
    data class EmailChanged(val email:String): SignInEvent()
    data class PasswordChanged(val password:String): SignInEvent()
    data object SignIn: SignInEvent()
}
