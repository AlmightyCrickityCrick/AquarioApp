package com.example.aquario.activities.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aquario.R
import com.example.aquario.activities.SensorDetailsActivity
import com.example.aquario.adapters.SensorAdapter
import com.example.aquario.data.generateSensors
import com.example.aquario.data.model.AquariumInfo
import com.example.aquario.data.model.SensorInfo
import com.example.aquario.listeners.SensorListener
import com.example.aquario.utils.GlobalUser
import com.example.aquario.utils.setMenuButton

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
    private var sensorInfo = generateSensors()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragment_toolbar)
        val toolbarFragmentName = toolbar.findViewById<TextView>(R.id.toolbar_fragment_name)
        if (GlobalUser.aquariums.size == 0) GlobalUser.aquariums.add(AquariumInfo("bcsrtc", "My Aquarium"))
        toolbarFragmentName.text = GlobalUser.aquariums[GlobalUser.currentAquarium].nickname
        activity?.let { setMenuButton(toolbar, it) }

        recyclerView = view.findViewById(R.id.sensor_view)
        initAdapter()

    }

    private fun initAdapter(){
        adapter = SensorAdapter(sensorInfo, this)
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