package com.example.aquario.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.aquario.R
import com.example.aquario.databinding.ActivityFeedingTimeSetBinding
import com.example.aquario.utils.feeding.FeedingViewModel

class FeedingTimeSetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedingTimeSetBinding
    var feedingViewModel = FeedingViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedingTimeSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarTimerSet.toolbarFragmentName.text = getString(R.string.feeding_timer)

        binding.toolbarTimerSet.toolbarBackButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            this.finish()
        }

        feedingViewModel.feedingResult.observe(this@FeedingTimeSetActivity, Observer {
            val feedingState = it ?: return@Observer

            if(feedingState.success != null) {
                setResult(Activity.RESULT_OK)
                this.finish()
            }
        })

        binding.btnSaveTime.setOnClickListener {
            var time = binding.pkFeedingTime.hour
            var stringTime = ""
            if(time < 10) stringTime = "0$time" else stringTime = "$time"
            var min = binding.pkFeedingTime.minute
            var formatted_min = ""
            if(min < 10) formatted_min = "0$min" else formatted_min = "$min"
            feedingViewModel.setFeedingTime("$stringTime:$formatted_min:00")
        }
    }
}