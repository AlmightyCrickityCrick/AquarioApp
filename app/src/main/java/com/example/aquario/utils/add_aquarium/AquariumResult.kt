package com.example.aquario.utils.add_aquarium

import com.example.aquario.data.model.AquariumInfo

data class AquariumResult(
    val success: AquariumInfo? = null,
    val error: Int? = null
)