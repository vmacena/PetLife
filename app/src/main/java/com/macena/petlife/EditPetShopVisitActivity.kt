package com.macena.petlife

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditPetShopVisitActivity : AppCompatActivity() {

    private lateinit var editPetShopVisitDate: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet_shop_visit)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit Last Pet Shop Visit"

        editPetShopVisitDate = findViewById(R.id.editPetShopVisitDate)
        val saveButton: Button = findViewById(R.id.savePetShopVisitButton)

        val currentPetShopVisitDate = intent.getStringExtra("lastPetShopVisit")
        editPetShopVisitDate.setText(currentPetShopVisitDate)

        saveButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("lastPetShopVisit", editPetShopVisitDate.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}