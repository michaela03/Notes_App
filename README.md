# 📒 NotesApp

**NotesApp** is a simple and user-friendly Android application for managing personal notes. This app allows you to easily create, view, update, and delete (CRUD) notes using **Java** and **SQLite**. It's perfect for anyone looking to practice Android development with core functionalities.

## 🚀 Features

- 📝 **Create Notes**: Add a new note with a title and description.
- 📋 **View Notes**: See a list of all saved notes on the main screen.
- 🔍 **View Note Details**: Tap on a note to see the full description.
- ✏️ **Edit Notes**: Long-press on a note to edit its content.
- ❌ **Delete Notes**: Long-press on a note to delete it.
- 🔙 **Navigation**: Easy navigation between screens with back buttons.

## 📂 Project Structure

```plaintext
NotesApp/
├── java/
│   ├── com.example.notesapp/
│   │   ├── MainActivity.java
│   │   ├── AddNoteActivity.java
│   │   ├── EditNoteActivity.java
│   │   ├── NoteDetailsActivity.java
│   │   ├── Note.java
│   │   └── NotesDatabase.java
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_add_note.xml
│   │   ├── activity_edit_note.xml
│   │   └── activity_note_details.xml
│   ├── values/
│   │   ├── strings.xml
│   │   └── colors.xml
│   └── drawable/
├── AndroidManifest.xml
└── README.md
