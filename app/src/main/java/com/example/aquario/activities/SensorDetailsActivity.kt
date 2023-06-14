package com.example.aquario.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.aquario.R
import com.example.aquario.data.model.SensorInfo
import com.example.aquario.databinding.ActivityLoginBinding
import com.example.aquario.databinding.ActivitySensorDetailsBinding
import com.example.aquario.utils.GlobalUser
import com.example.aquario.utils.sensor_fragment.SensorCollectionViewModel
import java.util.*

class SensorDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySensorDetailsBinding
    private var sensorCollectionViewModel = SensorCollectionViewModel()
    lateinit var sensorInfo: SensorInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var id = intent.getStringExtra("ID")
        if (id != null) {
            sensorCollectionViewModel.getSingleSensorInfo(id, GlobalUser.currentAquariumDetails.id)
        }

        binding = ActivitySensorDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val refresh = binding.btnRefresh
        val toolbar = binding.toolbarSensorDet
        val backButton = toolbar.toolbarBackButton
        val analytics = binding.btnAnalytics

        sensorCollectionViewModel.sensorDetailResult.observe(this@SensorDetailsActivity, Observer {
            val sensorInfoObs = it ?: return@Observer

            if (sensorInfoObs.success != null) {
                sensorInfo = sensorInfoObs.success
            }
            else{
                if (id != null) {
                    sensorInfo = GlobalUser.currentAquariumDetails.active_sensors!![id.toInt()]
                }
            }
            setUI()
        })

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

    private fun setUI(){
        binding.cardSensor.twSensorType.text = sensorInfo.type.capitalize(Locale.getDefault())
        var imageSRC :Int
        imageSRC = when(sensorInfo.type){
            "temperature" -> R.drawable.temperature_sensor_white
            "ph" -> R.drawable.ph_sensor_white
            "turbidity" -> R.drawable.turbidity_sensor_white
            "nitrate" -> R.drawable.nitrat_sensor_white
            "oxygen" ->R.drawable.o2_sensor_white
            "durity" -> R.drawable.duritate_sensor_white
            else -> R.drawable.ammonium_sensor_white
        }

        binding.cardSensor.iwSensorCardIcon.setImageResource(imageSRC)
        binding.sensorDataDetails.twSensorCardNow.text = sensorInfo.now.toString()
        binding.sensorDataDetails.twSensorCardRecommended.text = sensorInfo.recommended.toString()
        binding.sensorDataDetails.twSensorCardTime.text = sensorInfo.time
        binding.sensorName.text = sensorInfo.name


    }
}