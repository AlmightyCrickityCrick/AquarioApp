package com.example.aquario.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView

import android.widget.TextView
import com.example.aquario.R
import com.example.aquario.data.model.SensorInfo
import com.example.aquario.listeners.SensorListener

class SensorAdapter(var si:ArrayList<SensorInfo>, var sensorListener: SensorListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SensorCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_sensor, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SensorCardViewHolder){
            populateItemRows(holder, position)
        }
    }

    override fun getItemCount(): Int {
        return si.size
    }

    private class SensorCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var now:TextView
        var recommended :TextView
        var time:TextView
        var type:TextView
        var img : ImageView
        var details : TextView

        init {
            now = itemView.findViewById(R.id.tw_sensor_card_now)
            recommended = itemView.findViewById(R.id.tw_sensor_card_recommended)
            time=itemView.findViewById(R.id.tw_sensor_card_time)
            type=itemView.findViewById(R.id.tw_sensor_card_type)
            img=itemView.findViewById(R.id.iw_sensor_card_type_icon)
            details = itemView.findViewById(R.id.tw_sensor_card_details)
        }
    }


    private fun populateItemRows(viewHolder: SensorCardViewHolder, position: Int) {
        var item = si[position]
        viewHolder.now.text= item.now.toString()
        viewHolder.recommended.text= item.recommended.toString()
        viewHolder.time.text = item.time
        viewHolder.type.text = item.type

        var imageSRC :Int
        imageSRC = when(item.type){
            "temperature" -> R.drawable.temperature_sensor
            else -> R.drawable.ammonium_sensor
        }
        viewHolder.img.setImageResource(imageSRC)

        viewHolder.details.setOnClickListener { v-> sensorListener.onSensorClicked(item) }
    }

}