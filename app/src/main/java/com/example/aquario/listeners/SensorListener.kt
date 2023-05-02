package com.example.aquario.listeners

import com.example.aquario.data.model.SensorInfo

interface SensorListener {
   fun onSensorClicked(sens:SensorInfo)
}