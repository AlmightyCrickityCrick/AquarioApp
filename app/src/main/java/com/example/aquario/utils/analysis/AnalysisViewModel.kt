package com.example.aquario.utils.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aquario.utils.ApolloClientService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AnalysisViewModel :ViewModel(){
    private val _analysisResult = MutableLiveData<AnalysisResult>()
    val analysisResult: LiveData<AnalysisResult> = _analysisResult

    fun getAnalysis(senzor_type: Int, aquarium_id : String, interval: String){
        runBlocking {
            val job = GlobalScope.async { ApolloClientService.getAnalytics(senzor_type, aquarium_id, interval) }
            val result = job.await()
            if (result != null) {
                _analysisResult.value = AnalysisResult(success =  result)
            } else _analysisResult.value = AnalysisResult(error = 3)
        }
    }
}