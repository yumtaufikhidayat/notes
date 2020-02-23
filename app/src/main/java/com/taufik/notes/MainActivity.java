package com.taufik.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taufik.notes.activity.AddNoteActivity;
import com.taufik.notes.adapter.NotesAdapter;
import com.taufik.notes.room.model.Notes;
import com.taufik.notes.viewModel.NotesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;

    NotesViewModel notesViewModel;
    final NotesAdapter adapter = new NotesAdapter();
    RecyclerView recyclerView;
    FloatingActionButton fabAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();

        setFABOnClickListener();

        setRecyclerView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

                Notes notes = new Notes(title, description, priority);
                notesViewModel.insert(notes);

                Toast.makeText(this, R.string.tvNoteSaved, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.tvNoteNotSaved, Toast.LENGTH_SHORT).show();
        }
    }

    private void setFABOnClickListener() {
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
    }

    private void setInit() {
        fabAddNote = findViewById(R.id.fabNote);
    }

    private void setRecyclerView() {

        recyclerView = findViewById(R.id.recyclerViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                adapter.setNotes(notes);
            }
        });

        setItemTouchHelper();
    }

    private void setItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notesViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, R.string.tvNoteDeleted, Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteAllNotes:
                showDeleteAllNotes();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void showDeleteAllNotes() {

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);
        mAlertDialog.setMessage(R.string.tvConfirmDelete)
                .setNegativeButton(R.string.tvNo, null)
                .setPositiveButton(R.string.tvYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notesViewModel.deleteAllNotes();
                        Toast.makeText(MainActivity.this, R.string.tvNotesDeleted, Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialog = mAlertDialog.create();
        alertDialog.show();

    }
}
