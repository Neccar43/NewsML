package com.novacodestudios.news.viewmodel.sign_in

data class SignInState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)
