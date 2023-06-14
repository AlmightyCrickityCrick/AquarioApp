package com.example.aquario.data.model

data class AquariumDetail(val id: String, val code: String, val nickname: String, var feeding_time:String, var water_level: Double, var general_system_State: Double, var active_sensors: ArrayList<SensorInfo>?=null)