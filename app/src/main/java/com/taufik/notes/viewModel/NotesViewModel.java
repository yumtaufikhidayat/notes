package com.taufik.notes.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.taufik.notes.repository.NotesRepository;
import com.taufik.notes.room.model.Notes;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepository repository;
    private LiveData<List<Notes>> allNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        repository = new NotesRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Notes notes) {
        repository.insert(notes);
    }

    public void update(Notes notes) {
        repository.update(notes);
    }

    public void delete(Notes notes) {
        repository.delete(notes);
    }

    public void deleteaAllNotes(Notes notes) {
        repository.deleteAllNodes();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }
}
