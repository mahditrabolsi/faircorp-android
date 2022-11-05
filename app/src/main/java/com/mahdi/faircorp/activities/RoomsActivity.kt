package com.mahdi.faircorp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahdi.faircorp.R
import com.mahdi.faircorp.adapters.RoomAdapter
import com.mahdi.faircorp.adapters.RoomListener
import com.mahdi.faircorp.retrofit.ApiServices
import com.mahdi.faircorp.viewmodel.BaseViewModel
import com.mahdi.faircorp.viewmodel.RoomViewModel
import com.mahdi.faircorp.viewmodel.WindowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomsActivity : BaseActivity() , RoomListener {
    private val viewModel: RoomViewModel by viewModels {
        RoomViewModel.factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)
        val recyclerView = findViewById<RecyclerView>(R.id.Rooms_rv)
        val adapter = RoomAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel.findByBuildingId(intent.getLongExtra(BUILDING_ID,0)).observe(this) { rooms ->
            adapter.update(rooms)
        }
        viewModel.networkState.observe(this) { state ->
            if(state == BaseViewModel.State.OFFLINE) {
                Toast.makeText(this,"Offline mode, the last known values are displayed", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onWindowClicked(id: Long) {
        val intent = Intent(this, WindowsActivity::class.java).putExtra(ROOM_ID, id)
        startActivity(intent)
    }

    override fun onHeaterClicked(id: Long) {
        val intent = Intent(this, HeatersActivity::class.java).putExtra(ROOM_ID, id)
        startActivity(intent)
    }
}