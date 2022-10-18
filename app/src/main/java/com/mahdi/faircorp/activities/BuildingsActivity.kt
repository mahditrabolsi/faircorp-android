package com.mahdi.faircorp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahdi.faircorp.R
import com.mahdi.faircorp.adapters.BuildingAdapter
import com.mahdi.faircorp.adapters.OnBuildingSelectedListener
import com.mahdi.faircorp.retrofit.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuildingsActivity : BaseActivity(),OnBuildingSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.Buildings_rv)
        val adapter = BuildingAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        lifecycleScope.launch(context = Dispatchers.IO) {
            runCatching { ApiServices.buildingApiService.findAll().execute() }
                .onSuccess {
                    withContext(context = Dispatchers.Main) {
                        adapter.update(it.body() ?: emptyList())
                    }
                }
                .onFailure {
                    withContext(context = Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error on buildings loading $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
    override fun onBuildingSelected(id: Long) {
        val intent = Intent(this, RoomsActivity::class.java).putExtra(BUILDING_ID, id)
        startActivity(intent)
    }

}