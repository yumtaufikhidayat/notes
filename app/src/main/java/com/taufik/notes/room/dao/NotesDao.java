package com.taufik.notes.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.taufik.notes.room.model.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert
    void insetNotes(Notes notes);

    @Update
    void updateNotes(Notes notes);

    @Delete
    void deleteNotes(Notes notes);

    @Query("DELETE FROM notes_table")
    void deleteAllNotes();

    @Query("SELECT * FROM notes_table ORDER BY notesPriority DESC")
    LiveData<List<Notes>> getAllNotes();

}
