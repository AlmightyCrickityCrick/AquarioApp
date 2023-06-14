package com.example.aquario.activities.fragments

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.aquario.R
import com.example.aquario.activities.SensorDetailsActivity
import com.example.aquario.adapters.SensorAdapter
import com.example.aquario.data.generateSensors
import com.example.aquario.data.getSensorData
import com.example.aquario.data.model.AquariumInfo
import com.example.aquario.data.model.SensorInfo
import com.example.aquario.listeners.SensorListener
import com.example.aquario.utils.GlobalUser
import com.example.aquario.utils.sensor_fragment.SensorCollectionViewModel
import com.example.aquario.utils.setMenuButton
import me.itangqi.waveloadingview.WaveLoadingView
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SensorsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorsFragment : Fragment(), SensorListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SensorAdapter
    private var sensorInfoList = generateSensors()
    private var sensorCollectionViewModel = SensorCollectionViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        fetchAquariumInfo()

    }

    private fun fetchAquariumInfo() {
        sensorCollectionViewModel.getAquariumInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (GlobalUser.aquariums.size == 0) GlobalUser.aquariums.add(
            AquariumInfo(
                "bcsrtc",
                null,
                "My Aquarium"
            )
        )
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragment_toolbar)
        val toolbarFragmentName = toolbar.findViewById<TextView>(R.id.toolbar_fragment_name)

        var nr = view.findViewById<TextView>(R.id.tw_sensor_count)
        activity?.let { setMenuButton(toolbar, it) }

        recyclerView = view.findViewById(R.id.sensor_view)
        initAdapter()
        nr.text = sensorInfoList.size.toString()
        toolbarFragmentName.text =
            GlobalUser.aquariums[GlobalUser.currentAquarium].nickname
        setCardState(view)

        activity?.let {
            sensorCollectionViewModel.sensorResult.observe(it, Observer {
                val sensorInfo = it ?: return@Observer

                if (sensorInfo.success != null) {
                    toolbarFragmentName.text =
                        GlobalUser.aquariums[GlobalUser.currentAquarium].nickname
                    setCardState(view)
                    if(GlobalUser.currentAquariumDetails.active_sensors != null){
                        sensorInfoList = GlobalUser.currentAquariumDetails.active_sensors!!
                    } else {
                        sensorInfoList = generateSensors()
                        GlobalUser.currentAquariumDetails.active_sensors = sensorInfoList
                    }
                    initAdapter()
                    nr.text = sensorInfoList.size.toString()
                }

            })
        }


    }

    private fun setCardState(view: View) {
        var card = view.findViewById<LinearLayout>(R.id.state_card)
        var waveLoadingView = card.findViewById<WaveLoadingView>(R.id.waveLoadingView)
        waveLoadingView.centerTitle = GlobalUser.currentAquariumDetails.general_system_State.toInt().toString() + "%"
        card.findViewById<TextView>(R.id.tw_state_card_now).text = GlobalUser.currentAquariumDetails.water_level.toString()
        card.findViewById<TextView>(R.id.tw_state_card_recommended).text = getString(R.string.water_level_recommended)
        var calendar = java.util.Calendar.getInstance()
        var format = SimpleDateFormat("d MMM HH:mm")
        card.findViewById<TextView>(R.id.tw_state_card_time).text = format.format(calendar.time)
    }

    private fun initAdapter() {
        adapter = SensorAdapter(sensorInfoList, this)
        recyclerView.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SensorsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SensorsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSensorClicked(sens: SensorInfo) {
        activity?.let {
            var intent = Intent(it, SensorDetailsActivity::class.java)
            intent.putExtra("ID", sens.id)
            startActivity(intent)
        }
    }
}