package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NotesDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";

    public NotesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Метод за добавяне на бележка
    public void addNote(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Метод за извличане на всички бележки
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    notes.add(new Note(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                    ));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return notes;
    }

    // Метод за актуализиране на бележка по ID
    public void updateNote(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Метод за изтриване на бележка по ID
    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Нов метод: Извличане на бележка по ID
    public Note getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Note note = null;

        try {
            cursor = db.query(TABLE_NAME, null, COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                note = new Note(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                );
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return note;
    }

    // Нов метод: Изчистване на всички бележки
    public void clearAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}
