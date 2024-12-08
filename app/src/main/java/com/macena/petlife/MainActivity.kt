package com.macena.petlife

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.macena.petlife.database.SQLiteHelper

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewPets: RecyclerView
    private lateinit var adapter: PetAdapter
    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = SQLiteHelper(this)

        recyclerViewPets = findViewById(R.id.recyclerViewPets)
        recyclerViewPets.layoutManager = LinearLayoutManager(this)

        if (dbHelper.getPets().isEmpty()) {
            dbHelper.insertPet("Ted", "Dog")
            dbHelper.insertPet("Cheddar", "Dog")
            dbHelper.insertPet("Bacon", "Dog")
        }

        reloadPets()
    }

    private fun reloadPets() {
        val pets = dbHelper.getPets()
        adapter = PetAdapter(pets)

        adapter.onEditClick = { pet ->
            val intent = Intent(this, EditPetActivity::class.java)
            intent.putExtra("PET_ID", pet.id)
            startActivity(intent)
        }

        adapter.onRemoveClick = { pet ->
            dbHelper.deletePet(pet.id)
            reloadPets()
        }

        adapter.onViewEventsClick = { pet ->
            //android.util.Log.d("MainActivity", "Abrindo eventos para PET_ID: ${pet.id}")
            val intent = Intent(this, PetEventsActivity::class.java)
            intent.putExtra("PET_ID", pet.id)
            startActivity(intent)
        }


        recyclerViewPets.adapter = adapter
    }
}