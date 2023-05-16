package com.example.aquario.activities

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.aquario.R
import com.example.aquario.databinding.ActivityRegisterBinding
import com.example.aquario.utils.afterTextChanged
import com.example.aquario.utils.login.LoggedInUserView

import com.example.aquario.utils.register.RegisterViewModel
import com.example.aquario.utils.login.LoginViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val passwordConfirm = binding.passwordConfirm
        val signup = binding.signup
        val login = binding.btnToLogin
        val loading = binding.loading

        registerViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(RegisterViewModel::class.java)

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            // disable login button unless both username / password is valid
            signup.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
        })

        registerViewModel.loginResult.observe(this@RegisterActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null){

            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                setResult(Activity.RESULT_OK)
                intent = Intent(this, AddAquariumActivity::class.java)
                startActivity(intent)
                this.finish()
            }

        })

        username.afterTextChanged {
            registerViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                registerViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

        }

        login.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        signup.setOnClickListener {
            loading.visibility = View.VISIBLE
            registerViewModel.register(username.text.toString(), password.text.toString())
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }


}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
