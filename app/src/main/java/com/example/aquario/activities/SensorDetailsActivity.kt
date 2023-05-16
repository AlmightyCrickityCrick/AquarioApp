package com.example.aquario.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aquario.R
import com.example.aquario.databinding.ActivityLoginBinding
import com.example.aquario.databinding.ActivitySensorDetailsBinding

class SensorDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySensorDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySensorDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val refresh = binding.btnRefresh
        val toolbar = binding.toolbarSensorDet
        val backButton = toolbar.toolbarBackButton
        val analytics = binding.btnAnalytics

        backButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            this.finish()
        }

        refresh.setOnClickListener {
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT)
        }

        analytics.setOnClickListener {
//            intent = Intent(this, MainActivity::class.java)
//            setResult(Activity.RESULT_OK)
            //TODO: Figure out how to navigate this hellscape
            finish()
        }


    }
}