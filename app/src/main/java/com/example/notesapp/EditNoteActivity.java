package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    private Button btnUpdate;
    private NotesDatabase db;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = new NotesDatabase(this);

        // Активиране на бутона за връщане назад в заглавната лента
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Получаване на ID на бележката от Intent
        noteId = getIntent().getIntExtra("noteId", -1);
        if (noteId != -1) {
            Note note = db.getNoteById(noteId);
            if (note != null) {
                editTitle.setText(note.getTitle());
                editDescription.setText(note.getDescription());
            } else {
                Toast.makeText(this, "Бележката не е намерена", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        // Обновяване на бележката при натискане на бутона "Обнови"
        btnUpdate.setOnClickListener(v -> {
            String updatedTitle = editTitle.getText().toString().trim();
            String updatedDescription = editDescription.getText().toString().trim();

            if (updatedTitle.isEmpty() || updatedDescription.isEmpty()) {
                Toast.makeText(this, "Моля, попълнете всички полета", Toast.LENGTH_SHORT).show();
            } else {
                db.updateNote(noteId, updatedTitle, updatedDescription);
                Toast.makeText(this, "Бележката е обновена", Toast.LENGTH_SHORT).show();

                // Връщане към MainActivity след обновяване
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
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
