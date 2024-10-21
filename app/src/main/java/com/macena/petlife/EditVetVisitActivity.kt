package com.macena.petlife

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditVetVisitActivity : AppCompatActivity() {

    private lateinit var editVetVisitDate: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vet_visit)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit Vet Visit"

        editVetVisitDate = findViewById(R.id.editVetVisitDate)
        val saveButton: Button = findViewById(R.id.saveVetVisitButton)

        val currentVetVisitDate = intent.getStringExtra("lastVetVisit")
        editVetVisitDate.setText(currentVetVisitDate)

        saveButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("lastVetVisit", editVetVisitDate.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}