package com.example.aquario.utils.analysis

import com.example.aquario.data.model.AnalysisInfo

data class AnalysisResult (
    val success : AnalysisInfo?=null,
    val error:Int?=null
)