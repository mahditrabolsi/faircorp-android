package com.mahdi.faircorp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.mahdi.faircorp.R
import com.mahdi.faircorp.dto.HeaterDto
import com.mahdi.faircorp.dto.HeaterStatus
import com.mahdi.faircorp.dto.RoomDto

interface HeaterListener {
    fun onHeaterSwitched(id: Long)
    fun onHeaterChange(id: Long)
}

class HeaterAdapter(private val listener: HeaterListener) :
    RecyclerView.Adapter<HeaterAdapter.HeaterViewHolder>() {
inner class HeaterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val name: TextView = view.findViewById(R.id.heater_name)
    val power: TextView = view.findViewById(R.id.power)
    val switch: SwitchCompat = view.findViewById(R.id.heater_switch)
}

    private val items = mutableListOf<HeaterDto>()

    fun update(heaters: List<HeaterDto>) {
        items.clear()
        items.addAll(heaters)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.heater_item, parent, false)
        return HeaterViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaterViewHolder, position: Int) {
        val heater = items[position]
        holder.apply {

            name.text = heater.name
            power.text = heater.power.toString()
            switch.isChecked = heater.heaterStatus == HeaterStatus.ON
            switch.setOnCheckedChangeListener { _, _ ->

                listener.onHeaterSwitched(heater.id!!)
            }
            itemView.setOnClickListener {
                listener.onHeaterChange(heater.id!!)
            }

        }
    }

    override fun onViewRecycled(holder: HeaterViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            switch.setOnCheckedChangeListener(null)
        }
    }

    fun getHeaterById(id: Long): HeaterDto? {
        return items.find { it.id == id }
    }


}