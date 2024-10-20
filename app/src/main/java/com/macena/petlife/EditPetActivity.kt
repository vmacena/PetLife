package com.macena.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.macena.petlife.model.Pet
import com.macena.petlife.util.parcelable

class EditPetActivity : AppCompatActivity() {

    private lateinit var pet: Pet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet)

        pet = intent.parcelable("pet")!!

        val nameEditText = findViewById<EditText>(R.id.editPetName)
        val colorEditText = findViewById<EditText>(R.id.editPetColor)
        val sizeEditText = findViewById<EditText>(R.id.editPetSize)
        nameEditText.setText(pet.name)
        colorEditText.setText(pet.color)
        sizeEditText.setText(pet.size)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            pet.name = nameEditText.text.toString()
            pet.color = colorEditText.text.toString()
            pet.size = sizeEditText.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("pet", pet)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}