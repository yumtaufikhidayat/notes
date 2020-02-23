package com.taufik.notes.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.taufik.notes.room.NotesDatabase;
import com.taufik.notes.room.dao.NotesDao;
import com.taufik.notes.room.model.Notes;

import java.util.List;

public class NotesRepository {

    private NotesDao notesDao;
    private LiveData<List<Notes>> allNotes;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getInstance(application);
        notesDao = database.notesDao();
        allNotes = notesDao.getAllNotes();
    }

    public void insert(Notes notes) {
        new InsertNotesAsyncTask(notesDao).execute(notes);
    }

    public void update(Notes notes) {
        new UpdatesNotesAsyncTask(notesDao).execute(notes);
    }

    public void delete(Notes notes) {
        new DeleteNotesAsyncTask(notesDao).execute(notes);
    }

    public void deleteAllNodes() {
        new DeleteAllNotesNotesAsyncTask(notesDao).execute();
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNotesAsyncTask extends AsyncTask<Notes, Void, Void> {

        private NotesDao notesDao;

        private InsertNotesAsyncTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.insetNotes(notes[0]);
            return null;
        }

    }

    private static class UpdatesNotesAsyncTask extends AsyncTask<Notes, Void, Void> {

        private NotesDao notesDao;

        private UpdatesNotesAsyncTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.updateNotes(notes[0]);
            return null;
        }

    }

    private static class DeleteNotesAsyncTask extends AsyncTask<Notes, Void, Void> {

        private NotesDao notesDao;

        private DeleteNotesAsyncTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.deleteNotes(notes[0]);
            return null;
        }

    }

    private static class DeleteAllNotesNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NotesDao notesDao;

        private DeleteAllNotesNotesAsyncTask(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.deleteAllNotes();
            return null;
        }

    }
}
