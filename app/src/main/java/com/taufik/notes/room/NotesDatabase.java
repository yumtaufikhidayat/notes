package com.taufik.notes.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.taufik.notes.room.dao.NotesDao;
import com.taufik.notes.room.model.Notes;

@Database(entities = {Notes.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance;

    public abstract NotesDao notesDao();

    public static synchronized NotesDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class, "notes_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NotesDao notesDao;

        private PopulateDbAsyncTask(NotesDatabase db) {
            notesDao = db.notesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.insetNotes(new Notes("Title 1", "Description 1", 1));
            notesDao.insetNotes(new Notes("Title 2", "Description 2", 2));
            notesDao.insetNotes(new Notes("Title 3", "Description 3", 3));

            return null;
        }
    }
}
