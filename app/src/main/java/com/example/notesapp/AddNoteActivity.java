package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    private Button btnSave, btnBack;
    private NotesDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        db = new NotesDatabase(this);

        // Активиране на бутона за връщане назад в заглавната лента
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Логика за запазване на бележката
        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String description = editDescription.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please enter info", Toast.LENGTH_SHORT).show();
            } else {
                db.addNote(title, description);
                Toast.makeText(this, "Note is succesfully added", Toast.LENGTH_SHORT).show();

                // Връщане към MainActivity след добавяне на бележка
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Логика за връщане назад при натискане на бутона "Назад"
        btnBack.setOnClickListener(view -> finish());
    }

    // Реакция при натискане на бутона за връщане назад в заглавната лента
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Връща се към предишната активност
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
