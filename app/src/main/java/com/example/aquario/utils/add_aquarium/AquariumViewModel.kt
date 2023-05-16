package com.example.aquario.utils.add_aquarium

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aquario.R
import com.example.aquario.data.Result
import com.example.aquario.data.model.AquariumInfo
import com.example.aquario.utils.GlobalUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AquariumViewModel {
    private val _aquariumForm = MutableLiveData<AquariumFormState>()
    val aquariumForm: LiveData<AquariumFormState> = _aquariumForm

    private val _aquariumResult = MutableLiveData<AquariumResult>()
    val aquariumResult: LiveData<AquariumResult> = _aquariumResult

    fun registerAquarium(code:String, nickname: String){
        GlobalUser.aquariums.add(AquariumInfo(code, nickname))
        _aquariumResult.value = AquariumResult(success = AquariumInfo(code, nickname))
    }

    fun aquariumDataChanged(code: String, nickname: String) {
        if (!isCodeValid(code)) {
            _aquariumForm.value = AquariumFormState(codeError = R.string.invalid_code)
        } else if (!isNicknameValid(nickname)) {
            _aquariumForm.value = AquariumFormState(nicknameError = R.string.invalid_nickname)
        } else {
            _aquariumForm.value = AquariumFormState(isDataValid = true)
        }
    }

    private fun isCodeValid(code: String): Boolean {
        return code.length < 20
    }

    // A placeholder password validation check
    private fun isNicknameValid(nickname: String): Boolean {
        return nickname.length in 2..9
    }


}