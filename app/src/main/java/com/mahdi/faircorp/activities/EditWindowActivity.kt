package com.mahdi.faircorp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.viewModels
import com.mahdi.faircorp.R
import com.mahdi.faircorp.dto.RoomDto
import com.mahdi.faircorp.dto.WindowDto
import com.mahdi.faircorp.dto.WindowStatus
import com.mahdi.faircorp.viewmodel.WindowViewModel

class EditWindowActivity : AppCompatActivity() {

    private val viewModel: WindowViewModel by viewModels {
        WindowViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_window)
        val windowID = intent.getLongExtra(WINDOW_ID, 0)
        val roomID = intent.getLongExtra(ROOM_ID, 0)
        val editTextName = findViewById<EditText>(R.id.editTextWindowName)
        val radioGroupWindow = findViewById<RadioGroup>(R.id.radioGroupWindowStatus)
        val saveButton = findViewById<Button>(R.id.buttonSaveWindow)
        if (intent.getStringExtra(Window_NAME) != null) {
            editTextName.setText(intent.getStringExtra(Window_NAME))
            when (intent.getStringExtra(Window_STATUS)) {
                "OPEN" -> radioGroupWindow.check(R.id.radioButtonWindowOpen)
                "CLOSED" -> radioGroupWindow.check(R.id.radioButtonWindowClosed)
            }
        }
        saveButton.setOnClickListener {
            val name = editTextName.text.toString()
            val status = when (radioGroupWindow.checkedRadioButtonId) {
                R.id.radioButtonWindowOpen -> WindowStatus.OPEN
                R.id.radioButtonWindowClosed -> WindowStatus.CLOSED
                else -> WindowStatus.CLOSED
            }
            viewModel.createWindow(WindowDto(
                id=if (windowID == 0L) null else windowID,
                name = name,
                windowStatus = status,
                roomId = roomID))
                .observe(this) {
                    finish()
                }
        }

    }
}