package com.macena.petlife

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.macena.petlife.database.SQLiteHelper

class EditEventActivity : AppCompatActivity() {

    private lateinit var editTextEventType: EditText
    private lateinit var editTextEventDate: EditText
    private lateinit var editTextEventTime: EditText
    private lateinit var buttonSaveEvent: Button
    private lateinit var dbHelper: SQLiteHelper
    private var eventId: Int = -1
    private var petId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)

        dbHelper = SQLiteHelper(this)

        editTextEventType = findViewById(R.id.editTextEventType)
        editTextEventDate = findViewById(R.id.editTextEventDate)
        editTextEventTime = findViewById(R.id.editTextEventTime)
        buttonSaveEvent = findViewById(R.id.buttonSaveEvent)

        eventId = intent.getIntExtra("EVENT_ID", -1)
        petId = intent.getIntExtra("PET_ID", -1)

        if (eventId != -1) {
            val event = dbHelper.getEventById(eventId)
            if (event != null) {
                editTextEventType.setText(event.type)
                editTextEventDate.setText(event.date)
                editTextEventTime.setText(event.time)
            }
        }

        buttonSaveEvent.setOnClickListener {
            val type = editTextEventType.text.toString()
            val date = editTextEventDate.text.toString()
            val time = editTextEventTime.text.toString()


            if (type.isBlank() || date.isBlank() || time.isBlank()) {
                Toast.makeText(this,
                    "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (eventId == -1) {
                dbHelper.insertEvent(petId, type, date, time)
            } else {
                dbHelper.updateEvent(eventId, type, date, time)
            }

            Toast.makeText(this, "Event saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
