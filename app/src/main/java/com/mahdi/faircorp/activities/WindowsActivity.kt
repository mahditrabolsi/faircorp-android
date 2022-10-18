package com.mahdi.faircorp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahdi.faircorp.R
import com.mahdi.faircorp.adapters.HeaterAdapter
import com.mahdi.faircorp.adapters.WindowAdapter
import com.mahdi.faircorp.adapters.WindowListener
import com.mahdi.faircorp.retrofit.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WindowsActivity : AppCompatActivity(),WindowListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        val recyclerView = findViewById<RecyclerView>(R.id.Window_rv)
        val adapter = WindowAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices.windowApiService.findWindowsByRoomId(intent.getLongExtra(ROOM_ID,0)).execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on rooms loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    override fun onWindowSwitched(id: Long) {
        TODO("Not yet implemented")
    }
}