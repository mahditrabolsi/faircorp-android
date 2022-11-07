package com.mahdi.faircorp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.mahdi.faircorp.R
import com.mahdi.faircorp.dto.BuildingDto
import com.mahdi.faircorp.viewmodel.BaseViewModel
import com.mahdi.faircorp.viewmodel.BuildingViewModel


class NewBuildingActivity : AppCompatActivity() {
    private val viewModel: BuildingViewModel by viewModels {
        BuildingViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_building)
        val editTextBuildingName = findViewById<EditText>(R.id.editTextBuildingName)
        val editTextBuildingAddress = findViewById<EditText>(R.id.editTextBuildingAddress)
        val submitButton = findViewById<Button>(R.id.SubmitBuilding)
        val id = intent.getLongExtra(BUILDING_ID, 0)
        if (intent.getStringExtra(BUILDING_NAME) != null) {
            editTextBuildingName.setText(intent.getStringExtra(BUILDING_NAME))
            editTextBuildingAddress.setText(intent.getStringExtra(BUILDING_ADDRESS))
        }
        submitButton.setOnClickListener {
            val buildingName = editTextBuildingName.text.toString()
            val buildingAddress = editTextBuildingAddress.text.toString()
            if (id != 0L) {
                viewModel.createBuilding(
                    BuildingDto(
                        id=id,
                        name = buildingName,
                        address = buildingAddress
                    )
                ).observe(this) {

                    Toast.makeText(this, "Building Updated", Toast.LENGTH_LONG).show()
                    finish()
                }
            } else {
                viewModel.createBuilding(
                    BuildingDto(
                        name = buildingName,
                        address = buildingAddress
                    )
                ).observe(this) {

                    Toast.makeText(this, "Building created", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

}