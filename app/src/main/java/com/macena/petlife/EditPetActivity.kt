package com.macena.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.macena.petlife.model.Pet
import com.macena.petlife.util.parcelable

class EditPetActivity : AppCompatActivity() {

    private lateinit var pet: Pet
    private lateinit var photoUrlEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit Pet"

        pet = intent.parcelable("pet")!!

        val editPetName: EditText = findViewById(R.id.editPetName)
        val editPetBirthDate: EditText = findViewById(R.id.editPetBirthDate)
        val editPetColor: EditText = findViewById(R.id.editPetColor)
        val editPetSize: EditText = findViewById(R.id.editPetSize)
        val editPetTypeSpinner: Spinner = findViewById(R.id.editPetTypeSpinner)
        photoUrlEditText = findViewById(R.id.photoUrlEditText)
        val saveButton: Button = findViewById(R.id.saveButton)

        editPetName.setText(pet.name)
        editPetBirthDate.setText(pet.birthDate)
        editPetColor.setText(pet.color)
        editPetSize.setText(pet.size)
        photoUrlEditText.setText(pet.photoUrl)

        ArrayAdapter.createFromResource(
            this,
            R.array.pet_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editPetTypeSpinner.adapter = adapter
        }

        val petTypes = resources.getStringArray(R.array.pet_types)
        val petTypeIndex = petTypes.indexOf(pet.type)
        if (petTypeIndex >= 0) {
            editPetTypeSpinner.setSelection(petTypeIndex)
        }

        saveButton.setOnClickListener {
            pet.name = editPetName.text.toString()
            pet.birthDate = editPetBirthDate.text.toString()
            pet.color = editPetColor.text.toString()
            pet.size = editPetSize.text.toString()
            pet.type = editPetTypeSpinner.selectedItem.toString()
            pet.photoUrl = photoUrlEditText.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("pet", pet)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}