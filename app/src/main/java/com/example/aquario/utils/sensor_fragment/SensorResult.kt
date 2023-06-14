package com.example.aquario.utils.sensor_fragment

import com.example.aquario.data.model.AquariumDetail

data class SensorResult(
    val success: AquariumDetail? = null,
    val error: Int? = null
)