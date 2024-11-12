package com.example.notesapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailsActivity extends AppCompatActivity {

    private TextView textTitle, textDescription;
    private NotesDatabase db;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        db = new NotesDatabase(this);

        // Активиране на бутона за връщане назад в заглавната лента
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Извличане на ID на бележката от Intent
        noteId = getIntent().getIntExtra("noteId", -1);

        if (noteId != -1) {
            // Използваме новия метод getNoteById()
            Note note = db.getNoteById(noteId);

            if (note != null) {
                textTitle.setText(note.getTitle());
                textDescription.setText(note.getDescription());
            } else {
                Toast.makeText(this, "Бележката не е намерена", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Невалиден ID на бележка", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Реакция при натискане на бутона за връщане назад в заглавната лента
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
