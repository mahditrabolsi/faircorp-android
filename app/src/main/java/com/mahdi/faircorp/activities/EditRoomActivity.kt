package com.mahdi.faircorp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.mahdi.faircorp.R
import com.mahdi.faircorp.dto.RoomDto
import com.mahdi.faircorp.viewmodel.RoomViewModel

class EditRoomActivity : BaseActivity() {
    private val viewModel: RoomViewModel by viewModels {
        RoomViewModel.factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_room)

        val id = intent.getLongExtra(ROOM_ID, 0)
        val buildingId = intent.getLongExtra(BUILDING_ID, 0)
        val editTextName = findViewById<EditText>(R.id.editTextRoomName)
        val editTextFloor = findViewById<EditText>(R.id.editTextRoomFloor)
        val editTextCurrentTemperature = findViewById<EditText>(R.id.editTextCurrentTemperature)
        val editTextTargetTemperature = findViewById<EditText>(R.id.editTextTargetTemperature)
        val saveButton = findViewById<Button>(R.id.buttonSaveRoom)
        if (intent.getStringExtra(ROOM_NAME) != null) {
         editTextName.setText(intent.getStringExtra(ROOM_NAME))
            editTextFloor.setText(intent.getLongExtra(ROOM_FLOOR,0).toString())
            editTextCurrentTemperature.setText(intent.getDoubleExtra(ROOM_CURRENT_TEMPERATURE, 0.0).toString())
            editTextTargetTemperature.setText(intent.getDoubleExtra(ROOM_TARGET_TEMPERATURE, 0.0).toString())
             }
        saveButton.setOnClickListener {
            val name = editTextName.text.toString()
            val floor = editTextFloor.text.toString().toLongOrNull()
            val currentTemperature = editTextCurrentTemperature.text.toString().toDoubleOrNull()
            val targetTemperature = editTextTargetTemperature.text.toString().toDoubleOrNull()
            viewModel.createRoom(
                RoomDto(
                    id = if (id == 0L) null else id,
                    name = name,
                    floor = floor,
                    currentTemperature = currentTemperature,
                    targetTemperature = targetTemperature,
                    buildingId = buildingId
                )
            )
                .observe(this) {
                    finish()
                }

        }


    }
}