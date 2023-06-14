package com.example.aquario.utils.sensor_fragment

import com.example.aquario.data.model.SensorInfo
data class SensorDetailResult(
    val success: SensorInfo? = null,
    val error: Int? = null
)