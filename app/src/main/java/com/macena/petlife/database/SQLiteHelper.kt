package com.macena.petlife.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.macena.petlife.Event
import com.macena.petlife.Pet

class SQLiteHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_PETS_TABLE)
        db.execSQL(CREATE_EVENTS_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE pets ADD COLUMN birth_date TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE pets ADD COLUMN color TEXT NOT NULL DEFAULT ''")
            db.execSQL("ALTER TABLE pets ADD COLUMN size TEXT NOT NULL DEFAULT ''")
        }
    }

    fun getPets(): List<Pet> {
        val pets = mutableListOf<Pet>()
        val db = readableDatabase
        val cursor = db.query(
            "pets",
            arrayOf("id", "name", "type", "birth_date", "color", "size"),
            null,
            null,
            null,
            null,
            "name ASC"
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                val birthDate = cursor.getString(cursor.getColumnIndexOrThrow("birth_date"))
                val color = cursor.getString(cursor.getColumnIndexOrThrow("color"))
                val size = cursor.getString(cursor.getColumnIndexOrThrow("size"))
                pets.add(Pet(id, name, type, birthDate, color, size))
            }
            cursor.close()
        }

        db.close()
        return pets
    }


    fun insertPet(name: String, type: String, birthDate: String, color: String, size: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("type", type)
            put("birth_date", birthDate)
            put("color", color)
            put("size", size)
        }
        val id = db.insert("pets", null, values)
        db.close()
        return id
    }


    fun deletePet(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_PETS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getPetById(id: Int): Pet? {
        val db = readableDatabase
        val cursor = db.query(
            "pets",
            arrayOf("id", "name", "type", "birth_date", "color", "size"),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var pet: Pet? = null
        if (cursor != null && cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            val birthDate = cursor.getString(cursor.getColumnIndexOrThrow("birth_date"))
            val color = cursor.getString(cursor.getColumnIndexOrThrow("color"))
            val size = cursor.getString(cursor.getColumnIndexOrThrow("size"))
            pet = Pet(id, name, type, birthDate, color, size)
            cursor.close()
        }
        db.close()
        return pet
    }

    fun updatePet(id: Int, name: String, type: String, birthDate: String, color: String, size: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("type", type)
            put("birth_date", birthDate)
            put("color", color)
            put("size", size)
        }
        val rows = db.update("pets", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }


    fun getEventsByPetId(petId: Int): List<Event> {
        val events = mutableListOf<Event>()
        val db = readableDatabase
        val cursor = db.query(
            "events",
            arrayOf("id", "type", "date"),
            "pet_id = ?",
            arrayOf(petId.toString()),
            null,
            null,
            "date ASC"
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                events.add(Event(id, type, date))
            }
            cursor.close()
        }

        db.close()
        return events
    }

    fun deleteEvent(id: Int) {
        val db = writableDatabase
        db.delete("events", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun insertEvent(petId: Int, type: String, date: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("pet_id", petId)
            put("type", type)
            put("date", date)
        }
        val id = db.insert("events", null, values)
        db.close()
        return id
    }

    fun getEventById(eventId: Int): Event? {
        val db = readableDatabase
        val cursor = db.query(
            "events",
            arrayOf("id", "type", "date"),
            "id = ?",
            arrayOf(eventId.toString()),
            null,
            null,
            null
        )
        var event: Event? = null
        if (cursor != null && cursor.moveToFirst()) {
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
            event = Event(eventId, type, date)
            cursor.close()
        }
        db.close()
        return event
    }

    fun updateEvent(eventId: Int, type: String, date: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("type", type)
            put("date", date)
        }
        val rows = db.update(
            "events", values,
            "id = ?", arrayOf(eventId.toString()))
        db.close()
        return rows
    }

    companion object {
        private const val DATABASE_NAME = "PetLife.db"
        private const val DATABASE_VERSION = 3

        const val TABLE_PETS = "pets"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"

        private const val CREATE_PETS_TABLE = """
        CREATE TABLE pets (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL,
        type TEXT NOT NULL,
        birth_date TEXT NOT NULL,
        color TEXT NOT NULL,
        size TEXT NOT NULL
        )
"""


        private const val CREATE_EVENTS_TABLE = """
    CREATE TABLE events (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        pet_id INTEGER NOT NULL,
        type TEXT NOT NULL,
        date TEXT NOT NULL,
        FOREIGN KEY(pet_id) REFERENCES pets(id)
    )
"""

    }
}
