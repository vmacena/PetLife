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
    private lateinit var editTextPetBirthDate: EditText
    private lateinit var editTextPetColor: EditText
    private lateinit var editTextPetSize: EditText

    private lateinit var dbHelper: SQLiteHelper
    private var petId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet)

        dbHelper = SQLiteHelper(this)

        editTextPetName = findViewById(R.id.editTextPetName)
        editTextPetType = findViewById(R.id.editTextPetType)
        buttonSavePet = findViewById(R.id.buttonSavePet)
        editTextPetBirthDate = findViewById(R.id.editTextPetBirthDate)
        editTextPetColor = findViewById(R.id.editTextPetColor)
        editTextPetSize = findViewById(R.id.editTextPetSize)

        petId = intent.getIntExtra("PET_ID", -1)

        if (petId != -1) {
            val pet = dbHelper.getPetById(petId)
            if (pet != null) {
                editTextPetName.setText(pet.name)
                editTextPetType.setText(pet.type)
                editTextPetBirthDate.setText(pet.birthDate)
                editTextPetColor.setText(pet.color)
                editTextPetSize.setText(pet.size)
            }
        }

        buttonSavePet.setOnClickListener {
            val name = editTextPetName.text.toString()
            val type = editTextPetType.text.toString()
            val birthDate = editTextPetBirthDate.text.toString()
            val color = editTextPetColor.text.toString()
            val size = editTextPetSize.text.toString()

            if (name.isBlank() || type.isBlank() || birthDate.isBlank() || color.isBlank() || size.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (petId == -1) {
                dbHelper.insertPet(name, type, birthDate, color, size)
            } else {
                dbHelper.updatePet(petId, name, type, birthDate, color, size)
            }

            Toast.makeText(this, "Pet saved!", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}
