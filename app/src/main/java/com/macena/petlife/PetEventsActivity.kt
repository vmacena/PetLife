package com.macena.petlife

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.macena.petlife.database.SQLiteHelper

class PetEventsActivity : AppCompatActivity() {

    private lateinit var listViewEvents: ListView
    private lateinit var fabAddEvent: FloatingActionButton
    private lateinit var dbHelper: SQLiteHelper
    private var petId: Int = -1
    private lateinit var events: MutableList<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_events)

        dbHelper = SQLiteHelper(this)
        petId = intent.getIntExtra("PET_ID", -1)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listViewEvents = findViewById(R.id.listViewEvents)
        fabAddEvent = findViewById(R.id.fabAddEvent)

        registerForContextMenu(listViewEvents)

        fabAddEvent.setOnClickListener {
            val intent = Intent(this, EditEventActivity::class.java)
            intent.putExtra("PET_ID", petId)
            startActivity(intent)
        }

        reloadEvents()
    }

    private fun reloadEvents() {
        events = dbHelper.getEventsByPetId(petId).toMutableList()
        val eventNames = events.map { "${it.type} (${it.date}) - (${it.time})" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventNames)
        listViewEvents.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        reloadEvents()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == R.id.listViewEvents) {
            menu?.add(0, 1, 0, "Edit")
            menu?.add(0, 2, 1, "Delete")
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val event = events[info.position]

        return when (item.itemId) {
            1 -> {
                val intent = Intent(this, EditEventActivity::class.java)
                intent.putExtra("EVENT_ID", event.id)
                intent.putExtra("PET_ID", petId)
                startActivity(intent)
                true
            }
            2 -> {
                dbHelper.deleteEvent(event.id)
                reloadEvents()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
