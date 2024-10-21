package com.macena.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditVaccinationActivity : AppCompatActivity() {

    private lateinit var editVaccinationDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vaccination)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit Vaccination"

        editVaccinationDate = findViewById(R.id.editVaccinationDate)
        val saveButton: Button = findViewById(R.id.saveVaccinationButton)

        val currentVaccinationDate = intent.getStringExtra("lastVaccination")
        editVaccinationDate.setText(currentVaccinationDate)

        saveButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("lastVaccination", editVaccinationDate.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}