package com.example.aquario.data

import com.example.aquario.data.model.SensorInfo

fun generateSensors(): ArrayList<SensorInfo> {
    var tmp = ArrayList<SensorInfo>()
    for (i in 1..7){
        tmp.add(SensorInfo(i, "temperature", 24, 22, "02 May 19:08"))
    }
    return tmp
}