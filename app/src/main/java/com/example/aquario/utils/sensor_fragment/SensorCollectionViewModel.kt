package com.example.aquario.utils.sensor_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aquario.utils.ApolloClientService
import com.example.aquario.utils.GlobalUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class SensorCollectionViewModel :ViewModel(){

    private val _sensorResult = MutableLiveData<SensorResult>()
    val sensorResult: LiveData<SensorResult> = _sensorResult

    private val _sensorDetailResult = MutableLiveData<SensorDetailResult>()
    val sensorDetailResult: LiveData<SensorDetailResult> = _sensorDetailResult

    fun getAquariumInfo(){
        runBlocking {
            val job = GlobalScope.async { ApolloClientService.getAquariums() }
            val result = job.await()
            if (result != null) {
                GlobalUser.aquariums = result
                val j = GlobalScope.async { ApolloClientService.getAquariumDetails(GlobalUser.aquariums[GlobalUser.currentAquarium].id) }
                val r = j.await()
                if (r != null){
                    GlobalUser.currentAquariumDetails = r
                    _sensorResult.value = SensorResult(success = r)
                }
            }
        }
    }

    fun getSingleSensorInfo(sensor_id: String, aquarium_id: String){
        runBlocking {
            val job = GlobalScope.async { ApolloClientService.getSingleSensor(sensor_id, aquarium_id) }
            val result = job.await()
            if (result != null) {
                _sensorDetailResult.value = SensorDetailResult(success = result)
            } else _sensorDetailResult.value = SensorDetailResult(error = 2)
        }
    }
}