package com.example.aquario.data.model

data class AnalysisInfo(
    var senzor_id: String,
    var average_value: Double,
    var highest_value: Double,
    var current_value_status: Int,
    var value_list: ArrayList<AnalysisPoint>?
)

data class AnalysisPoint(var value: Double,
                         var time: String)