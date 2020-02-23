package com.taufik.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taufik.notes.activity.AddEditNoteActivity;
import com.taufik.notes.adapter.NotesAdapter;
import com.taufik.notes.room.model.Notes;
import com.taufik.notes.viewModel.NotesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    final NotesAdapter adapter = new NotesAdapter();
    NotesViewModel notesViewModel;
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
                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

                Notes notes = new Notes(title, description, priority);
                notesViewModel.insert(notes);

                Toast.makeText(this, R.string.tvNoteSaved, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            if (data != null) {
                int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

                if (id == -1) {
                    Toast.makeText(this, R.string.tvFailedEditNote, Toast.LENGTH_SHORT).show();
                    return;
                }

                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

                Notes notes = new Notes(title, description, priority);
                notes.setNotesId(id);

                notesViewModel.update(notes);

                Toast.makeText(this, R.string.tvNoteUpdated, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, R.string.tvNoteNotSaved, Toast.LENGTH_SHORT).show();
        }
    }

    private void setFABOnClickListener() {
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
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
                adapter.submitList(notes);
            }
        });

        setItemTouchHelper();
    }

    private void setItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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

        adapter.setOnItemClickListener(new NotesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Notes notes) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, notes.getNotesId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, notes.getNotesTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, notes.getNotesDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, notes.getNotesPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deleteAllNotes) {
            showDeleteAllNotes();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
