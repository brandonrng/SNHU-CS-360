package com.example.cs360_bquinones

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EventDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "EventDatabase.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_EVENTS = "events"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_EVENTS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TITLE TEXT, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_EVENTS ADD COLUMN $COLUMN_TIME TEXT")
        }
    }

    fun addEvent(title: String, date: String, time: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TITLE, title)
        contentValues.put(COLUMN_DATE, date)
        contentValues.put(COLUMN_TIME, time)

        return try {
            db.insertOrThrow(TABLE_EVENTS, null, contentValues)
            true
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }

    fun getAllEvents(): List<Event> {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_EVENTS,
            arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_DATE, COLUMN_TIME),
            null,
            null,
            null,
            null,
            "$COLUMN_DATE ASC"
        )

        val events = mutableListOf<Event>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
                events.add(Event(id, title, date, time))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return events
    }
}