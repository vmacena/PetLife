package com.macena.petlife

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.macena.petlife.database.SQLiteHelper

class EditPetActivity : AppCompatActivity() {

    private lateinit var editTextPetName: EditText
    private lateinit var editTextPetType: EditText
    private lateinit var buttonSavePet: Button
    private lateinit var dbHelper: SQLiteHelper
    private var petId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet)

        dbHelper = SQLiteHelper(this)

        editTextPetName = findViewById(R.id.editTextPetName)
        editTextPetType = findViewById(R.id.editTextPetType)
        buttonSavePet = findViewById(R.id.buttonSavePet)

        petId = intent.getIntExtra("PET_ID", -1)

        if (petId != -1) {
            val pet = dbHelper.getPetById(petId)
            if (pet != null) {
                editTextPetName.setText(pet.name)
                editTextPetType.setText(pet.type)
            }
        }

        buttonSavePet.setOnClickListener {
            val name = editTextPetName.text.toString()
            val type = editTextPetType.text.toString()

            if (name.isBlank() || type.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (petId == -1) {
                dbHelper.insertPet(name, type)
            } else {
                dbHelper.updatePet(petId, name, type)
            }

            Toast.makeText(this, "Pet saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
