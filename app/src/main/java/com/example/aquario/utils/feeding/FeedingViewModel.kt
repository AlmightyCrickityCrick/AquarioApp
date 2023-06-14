package com.example.aquario.utils.feeding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aquario.utils.ApolloClientService
import com.example.aquario.utils.GlobalUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FeedingViewModel:ViewModel() {
    private val _feedingResult = MutableLiveData<FeedingResult>()
    val feedingResult: LiveData<FeedingResult> = _feedingResult


    fun setFeedingTime(time: String){
        runBlocking {
            val job = GlobalScope.async { ApolloClientService.modifyAquarium(GlobalUser.currentAquariumDetails.code, time) }
            val result = job.await()
            if (result != null) {
                _feedingResult.value = FeedingResult(success = 1)
            } else _feedingResult.value= FeedingResult(error = 2)
        }
    }
}