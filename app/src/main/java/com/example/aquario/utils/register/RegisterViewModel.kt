package com.example.aquario.utils.register

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aquario.R
import com.example.aquario.data.LoginRepository
import com.example.aquario.utils.login.LoginResult
import com.example.aquario.utils.login.LoginViewModel
import com.example.aquario.data.Result
import com.example.aquario.utils.login.LoggedInUserView
import kotlinx.coroutines.*

class RegisterViewModel (loginRepository: LoginRepository) : LoginViewModel(loginRepository) {

    private val _loginForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _loginForm

//    override val _loginResult = MutableLiveData<LoginResult>()
    override val loginResult: LiveData<LoginResult> = _loginResult

    @DelicateCoroutinesApi
    fun register(username: String, password: String) {
        runBlocking {
            val job = GlobalScope.async {
                loginRepository.register(username, password)
            }
            val result = job.await()
            if (result is Result.Success) {
                login(username, password)
                _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.email))
            } else {
                _loginResult.value = LoginResult(error = R.string.register_failed)
            }
        }
        }


    override fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        var hasSpecialSymbols = false
        var special = "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~"
        for(i in  special){
            if(i in password) hasSpecialSymbols = true
        }
        var upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var hasUpper = false
        for(i in  upper){
            if(i in password) hasUpper = true
        }
        return ((password.length >= 5) && hasUpper && hasSpecialSymbols)
    }
}