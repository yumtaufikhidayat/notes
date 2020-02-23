package com.taufik.notes.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int notesId;

    private String notesTitle;

    private String notesDescription;

    private int notesPriority;

    public Notes(String notesTitle, String notesDescription, int notesPriority) {
        this.notesTitle = notesTitle;
        this.notesDescription = notesDescription;
        this.notesPriority = notesPriority;
    }

    public int getNotesId() {
        return notesId;
    }

    public void setNotesId(int notesId) {
        this.notesId = notesId;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public String getNotesDescription() {
        return notesDescription;
    }

    public int getNotesPriority() {
        return notesPriority;
    }
}
