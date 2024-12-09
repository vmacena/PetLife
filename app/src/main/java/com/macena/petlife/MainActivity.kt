package com.macena.petlife

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.macena.petlife.database.SQLiteHelper

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewPets: RecyclerView
    private lateinit var adapter: PetAdapter
    private lateinit var dbHelper: SQLiteHelper

    private val addPetLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            reloadPets()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "PetLife - List of Pets"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        dbHelper = SQLiteHelper(this)

        recyclerViewPets = findViewById(R.id.recyclerViewPets)
        recyclerViewPets.layoutManager = LinearLayoutManager(this)

        val fabAddPet = findViewById<FloatingActionButton>(R.id.fabAddPet)
        fabAddPet.setOnClickListener {
            val intent = Intent(this, EditPetActivity::class.java)
            addPetLauncher.launch(intent)
        }

        reloadPets()
    }

    private fun reloadPets() {
        val pets = dbHelper.getPets()
        adapter = PetAdapter(pets)

        adapter.onEditClick = { pet ->
            val intent = Intent(this, EditPetActivity::class.java)
            intent.putExtra("PET_ID", pet.id)
            addPetLauncher.launch(intent)
        }

        adapter.onRemoveClick = { pet ->
            dbHelper.deletePet(pet.id)
            reloadPets()
        }

        adapter.onViewEventsClick = { pet ->
            val intent = Intent(this, PetEventsActivity::class.java)
            intent.putExtra("PET_ID", pet.id)
            startActivity(intent)
        }

        recyclerViewPets.adapter = adapter
    }
}
