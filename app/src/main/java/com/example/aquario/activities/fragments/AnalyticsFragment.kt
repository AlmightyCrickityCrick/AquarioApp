package com.example.aquario.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.example.aquario.R
import com.example.aquario.data.getSensorData
import com.example.aquario.data.getSpinnerOptions
import com.example.aquario.utils.GlobalUser
import com.example.aquario.utils.analysis.AnalysisViewModel
import com.example.aquario.utils.setMenuButton
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AnalyticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnalyticsFragment : Fragment(), OnItemSelectedListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var sensorsArray = generateSensorOptions()
    private var sensorData = getSensorData()

    private var currentSensorId = 0
    private var analysisViewModel = AnalysisViewModel()

    private lateinit var chart: LineChart

    private fun generateSensorOptions(): ArrayList<String>{
        var tmp = ArrayList<String>()
        for (s in GlobalUser.currentAquariumDetails.active_sensors!!){
           if(s.type != "ph") tmp.add(s.type.capitalize(Locale.getDefault()) + " Sensor")
           else tmp.add("pH Sensor")
        }

        return tmp
    }

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
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragment_toolbar)
        val toolbarFragmentName = toolbar.findViewById<TextView>(R.id.toolbar_fragment_name)
        toolbarFragmentName.text = getString(R.string.analytics)
        activity?.let { setMenuButton(toolbar, it) }
        setAdapter(view)
        setupChart(view)

    }

    private fun setupChart(view: View){
        chart = view.findViewById(R.id.sensor_line_chart)
        val xAxisValues: List<String> = ArrayList(
            listOf(
                "Jan",
                "Feb",
                "March",
                "April",
                "May",
                "June"
            )
        )

        val ourLineChartEntries: ArrayList<Entry> = ArrayList()

        for (v in sensorData){
            ourLineChartEntries.add(Entry(sensorData.indexOf(v).toFloat(), v.toFloat()))
        }
        val lineDataSet = LineDataSet(ourLineChartEntries, "")
        lineDataSet.setColors(*ColorTemplate.PASTEL_COLORS)
        lineDataSet.valueTextSize = 10f

        val data = LineData(lineDataSet)
        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM;
        chart.xAxis.setDrawLabels(true)
        chart.axisRight.isEnabled = false


        chart.legend.isEnabled = false
        chart.description.isEnabled = false

        chart.animateX(1000, Easing.EaseInSine)
        chart.data = data
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        //refresh
        chart.invalidate()

    }


    private fun setAdapter(view:View){
        val spin = view.findViewById<Spinner>(R.id.spinner_sensors)
        spin.onItemSelectedListener = this

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            activity!!.applicationContext,
            R.layout.spinner_item_layout,
            sensorsArray as List<String>
        )

        ad.setDropDownViewResource(
            R.layout.spinner_dropdown_item_layout)

        spin.adapter = ad
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AnalyticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AnalyticsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(activity!!.applicationContext, sensorsArray[position], Toast.LENGTH_SHORT).show()
        currentSensorId = position
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}