package com.example.aquario.data

import com.example.aquario.data.model.SensorInfo
import java.text.SimpleDateFormat

fun generateSensors(): ArrayList<SensorInfo> {
    var tmp = ArrayList<SensorInfo>()
    var calendar = java.util.Calendar.getInstance()
    var format = SimpleDateFormat("d MMM HH:mm")
    tmp.add(SensorInfo("0", "temperature", 24, 22, format.format(calendar.time)))
    tmp.add(SensorInfo("2", "turbidity", 2, 1, format.format(calendar.time)))
    tmp.add(SensorInfo("3", "ammonia", 1, 0, format.format(calendar.time)))
    tmp.add(SensorInfo("4", "nitrate", 5, 5, format.format(calendar.time)))
    tmp.add(SensorInfo("5", "oxygen", 90, 100, format.format(calendar.time)))
    tmp.add(SensorInfo("1", "ph", 7, 7, format.format(calendar.time)))
    tmp.add(SensorInfo("6", "durity", 5, 4, format.format(calendar.time)))

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