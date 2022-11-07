package com.mahdi.faircorp.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mahdi.faircorp.R
import com.mahdi.faircorp.adapters.BuildingAdapter
import com.mahdi.faircorp.adapters.OnBuildingSelectedListener
import com.mahdi.faircorp.viewmodel.BaseViewModel
import com.mahdi.faircorp.viewmodel.BuildingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuildingsActivity : BaseActivity(),OnBuildingSelectedListener {
    private val viewModel: BuildingViewModel by viewModels {
        BuildingViewModel.factory
    }
    val adapter = BuildingAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.Buildings_rv)

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
    fab.setOnClickListener {

        val intent= Intent(this, NewBuildingActivity::class.java)
        startActivity(intent)
    }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

    }
    override fun onBuildingSelected(id: Long) {
        val intent = Intent(this, RoomsActivity::class.java).putExtra(BUILDING_ID, id)
        startActivity(intent)
    }

    override fun onBuildingChange(id: Long) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_choose)
        dialog.show()
        dialog.findViewById<Button>(R.id.btnDelete).setOnClickListener {

            viewModel.deleteBuilding(id).observe(this) {

                lifecycleScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@BuildingsActivity, "Building deleted", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                }
            }

        }
        dialog.findViewById<Button>(R.id.btnEdit).setOnClickListener {
            val building =adapter.getBuildingById(id)
            val intent = Intent(this, NewBuildingActivity::class.java).putExtra(BUILDING_ID, id)
                .putExtra(BUILDING_NAME, building?.name).putExtra(
                    BUILDING_ADDRESS, building?.address
                )
            startActivity(intent)
            dialog.dismiss()
        }



    }

    override fun onResume() {
        super.onResume()
        viewModel.findAll().observe(this) { buildings ->
            adapter.update(buildings)
            viewModel.networkState.observe(this) { state ->
                if(state == BaseViewModel.State.OFFLINE) {
                    Toast.makeText(this,"Offline mode, the last known values are displayed", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

}