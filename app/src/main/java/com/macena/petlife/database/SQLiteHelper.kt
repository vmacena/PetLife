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
        db.execSQL("DROP TABLE IF EXISTS events")
        db.execSQL("DROP TABLE IF EXISTS pets")
        onCreate(db)
    }


    fun getPets(): List<Pet> {
        val pets = mutableListOf<Pet>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PETS,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_TYPE),
            null,
            null,
            null,
            null,
            "$COLUMN_NAME ASC"
        )

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                pets.add(Pet(id, name, type))
            }
            cursor.close()
        }

        db.close()
        return pets
    }

    fun insertPet(name: String, type: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_TYPE, type)
        }
        val id = db.insert(TABLE_PETS, null, values)
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
            TABLE_PETS,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_TYPE),
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var pet: Pet? = null
        if (cursor != null && cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
            pet = Pet(id, name, type)
            cursor.close()
        }
        db.close()
        return pet
    }

    fun updatePet(id: Int, name: String, type: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_TYPE, type)
        }
        val rows = db.update(
            TABLE_PETS, values,
            "$COLUMN_ID = ?", arrayOf(id.toString()))
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
        private const val DATABASE_VERSION = 2

        const val TABLE_PETS = "pets"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"

        private const val CREATE_PETS_TABLE = """
            CREATE TABLE $TABLE_PETS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_TYPE TEXT NOT NULL
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
