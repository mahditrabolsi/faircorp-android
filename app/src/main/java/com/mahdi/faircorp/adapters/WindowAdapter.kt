package com.mahdi.faircorp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.mahdi.faircorp.R
import com.mahdi.faircorp.dto.WindowDto
import com.mahdi.faircorp.dto.WindowStatus

interface WindowListener {
    fun onWindowSwitched(id: Long)
}
class WindowAdapter(private val listener: WindowListener) :
    RecyclerView.Adapter<WindowAdapter.WindowViewHolder>() {
    inner class WindowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.window_name)
        val switch: SwitchCompat = view.findViewById(R.id.window_switch)
    }
    private val items = mutableListOf<WindowDto>()
    fun update(windows: List<WindowDto>) {
        items.clear()
        items.addAll(windows)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WindowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.window_item, parent, false)
        return WindowViewHolder(view)
    }
    override fun onBindViewHolder(holder: WindowViewHolder, position: Int) {
        val window = items[position]
        holder.apply {
            name.text = window.name
            switch.isChecked = window.windowStatus == WindowStatus.OPEN
            switch.setOnCheckedChangeListener { _, _ ->
                listener.onWindowSwitched(window.id)
            }
        }
    }
    override fun onViewRecycled(holder: WindowViewHolder) {
        super.onViewRecycled(holder)
        holder.apply {
            switch.setOnCheckedChangeListener(null)
        }
    }

}