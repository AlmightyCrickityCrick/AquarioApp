package com.example.aquario.data

import com.example.aquario.data.model.SensorInfo

fun generateSensors(): ArrayList<SensorInfo> {
    var tmp = ArrayList<SensorInfo>()
    for (i in 1..7){
        tmp.add(SensorInfo(i, "temperature", 24, 22, "02 May 19:08"))
    }
    return tmp
}

fun getSpinnerOptions(): ArrayList<String>{
    var tmp = ArrayList<String>()
    tmp.add("Temperature Sensor Data")
    tmp.add("pH Sensor")
    tmp.add("O2 Sensor")
    tmp.add("Turbidity Sensor")
    return tmp
}

fun getSensorData(): ArrayList<Int>{
    var tmp = ArrayList<Int>()
    for (i in 15 .. 20) tmp.add(i)
    return tmp
}