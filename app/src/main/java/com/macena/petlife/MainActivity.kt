package com.macena.petlife

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.macena.petlife.model.Pet
import com.macena.petlife.util.parcelable

class MainActivity : AppCompatActivity() {

    private lateinit var pet: Pet
    private lateinit var editPetLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.subtitle = "SC3034518"
        supportActionBar?.title = getString(R.string.app_name)

        pet = Pet(
            "Bob",
            "08/03/2021",
            "Dog",
            "Gray",
            "Big",
            "05/06/2024",
            "05/06/2024",
            "18/09/2024",
            "https://br.freepik.com/fotos-gratis/close-up-de-um-cao-sentando-branco-fundo_2610789.htm#fromView=keyword&page=1&position=2&uuid=563d291f-8acd-4c31-86c1-586d67941bc4"
        )

        displayPetInfo()

        val petTypeSpinner: Spinner = findViewById(R.id.petTypeSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.pet_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            petTypeSpinner.adapter = adapter
        }
        petTypeSpinner.isEnabled = false

        editPetLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                pet = data?.parcelable("pet")!!
                displayPetInfo()
            }
        }

        findViewById<Button>(R.id.viewPhotoButton).setOnClickListener {
            pet.photoUrl?.let { photoUrl ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(photoUrl))
                startActivity(intent)
            }
        }
    }

    private fun displayPetInfo() {
        findViewById<TextView>(R.id.petName).text = pet.name
        findViewById<TextView>(R.id.petBirthDate).text = pet.birthDate
        findViewById<TextView>(R.id.petColor).text = pet.color
        findViewById<TextView>(R.id.petSize).text = pet.size
        findViewById<TextView>(R.id.lastVetVisit).text = pet.lastVetVisit
        findViewById<TextView>(R.id.lastVaccination).text = pet.lastVaccination
        findViewById<TextView>(R.id.lastPetShopVisit).text = pet.lastPetShopVisit
        findViewById<TextView>(R.id.photoUrl).text = pet.photoUrl

        val petTypeSpinner: Spinner = findViewById(R.id.petTypeSpinner)
        val petTypes = resources.getStringArray(R.array.pet_types)
        val petTypeIndex = petTypes.indexOf(pet.type)
        if (petTypeIndex >= 0) {
            petTypeSpinner.setSelection(petTypeIndex)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editPet -> {
                val intent = Intent(this, EditPetActivity::class.java)
                intent.putExtra("pet", pet)
                editPetLauncher.launch(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}