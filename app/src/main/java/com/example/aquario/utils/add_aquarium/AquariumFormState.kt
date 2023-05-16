package com.example.aquario.utils.add_aquarium

data class AquariumFormState(
    val codeError: Int? = null,
    val nicknameError: Int? = null,
    val isDataValid: Boolean = false
)