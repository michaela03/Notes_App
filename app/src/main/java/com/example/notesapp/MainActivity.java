package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnAddNote;
    NotesDatabase db;
    List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnAddNote = findViewById(R.id.btnAddNote);
        db = new NotesDatabase(this);

        btnAddNote.setOnClickListener(v -> startActivity(new Intent(this, AddNoteActivity.class)));

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Note note = notesList.get(position);
            Intent intent = new Intent(this, NoteDetailsActivity.class);
            intent.putExtra("noteId", note.getId());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Note note = notesList.get(position);
            showOptionsDialog(note);
            return true;
        });
        loadNotes();
    }

    private void loadNotes() {
        notesList = db.getAllNotes();
        List<String> titles = new ArrayList<>();
        for (Note note : notesList) {
            titles.add(note.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);
    }

    private void showOptionsDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Изберете опция")
                .setItems(new String[]{"Редактирай", "Изтрий"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(this, EditNoteActivity.class);
                        intent.putExtra("noteId", note.getId());
                        startActivity(intent);
                    } else {
                        db.deleteNote(note.getId());
                        loadNotes();
                    }
                }).show();
    }
}
