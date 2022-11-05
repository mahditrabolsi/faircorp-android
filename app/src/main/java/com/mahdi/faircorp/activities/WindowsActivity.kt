package com.mahdi.faircorp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahdi.faircorp.R
import com.mahdi.faircorp.adapters.WindowAdapter
import com.mahdi.faircorp.adapters.WindowListener
import com.mahdi.faircorp.retrofit.ApiServices
import com.mahdi.faircorp.viewmodel.WindowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.mahdi.faircorp.viewmodel.BaseViewModel.State

class WindowsActivity : AppCompatActivity(),WindowListener {
    private val viewModel: WindowViewModel by viewModels {
        WindowViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        val recyclerView = findViewById<RecyclerView>(R.id.Window_rv)
        val adapter = WindowAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        viewModel.findByRoomId(intent.getLongExtra(ROOM_ID,0)).observe(this) { windows ->
            adapter.update(windows)
        }
        viewModel.networkState.observe(this) { state ->
            if(state == State.OFFLINE) {
                Toast.makeText(this,"Offline mode, the last known values are displayed", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    override fun onWindowSwitched(id: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            runCatching { ApiServices.windowApiService.switchStatus(id).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Window switched",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on switching window $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}