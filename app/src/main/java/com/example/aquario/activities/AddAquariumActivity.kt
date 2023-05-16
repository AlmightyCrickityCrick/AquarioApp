package com.example.aquario.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.aquario.R
import com.example.aquario.databinding.ActivityAddAquariumBinding
import com.example.aquario.databinding.ActivityLoginBinding
import com.example.aquario.utils.add_aquarium.AquariumViewModel
import com.example.aquario.utils.afterTextChanged
import com.example.aquario.utils.login.LoginViewModel
import com.example.aquario.utils.login.LoginViewModelFactory

class AddAquariumActivity : AppCompatActivity() {
    private lateinit var aquariumViewModel: AquariumViewModel
    private lateinit var binding: ActivityAddAquariumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAquariumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val code = binding.code
        val nickname = binding.nickname
        val btnAddAquarium = binding.btnAddAquarium

        aquariumViewModel = AquariumViewModel()

        aquariumViewModel.aquariumForm.observe(this@AddAquariumActivity, Observer {
            val aquariumState = it ?: return@Observer

            // disable login button unless both username / password is valid
            btnAddAquarium.isEnabled = aquariumState.isDataValid

            if (aquariumState.codeError != null) {
                code.error = getString(aquariumState.codeError)
            }
            if (aquariumState.nicknameError != null) {
                nickname.error = getString(aquariumState.nicknameError)
            }
        })

        aquariumViewModel.aquariumResult.observe(this@AddAquariumActivity, Observer {
            val aquariumResult = it ?: return@Observer

            if (aquariumResult.error != null) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT)
            }
            if (aquariumResult.success != null) {
                intent = Intent(this, MainActivity::class.java)
                setResult(Activity.RESULT_OK)
                startActivity(intent)
                this.finish()
            }


        })

        code.afterTextChanged {
            aquariumViewModel.aquariumDataChanged(
                code.text.toString(),
                nickname.text.toString()
            )
        }

        nickname.apply {
            afterTextChanged {
                aquariumViewModel.aquariumDataChanged(
                    code.text.toString(),
                    nickname.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        aquariumViewModel.registerAquarium(
                            code.text.toString(),
                            nickname.text.toString()
                        )
                }
                false
            }

            btnAddAquarium.setOnClickListener {
                aquariumViewModel.registerAquarium(code.text.toString(), nickname.text.toString())
            }
        }
    }
}