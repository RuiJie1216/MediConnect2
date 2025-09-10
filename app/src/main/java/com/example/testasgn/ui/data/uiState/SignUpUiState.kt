package com.example.testasgn.ui.data.uiState

data class SignUpUiState(
    val existPatient: Boolean? = null,
    val ic: String = "",
    val name: String = "",
    val pwd: String = "",
    val email: String = "",
    val phone: String = "",
    val read: Boolean = false
)