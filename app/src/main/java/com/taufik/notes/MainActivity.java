package com.taufik.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.taufik.notes.adapter.NotesAdapter;
import com.taufik.notes.room.model.Notes;
import com.taufik.notes.viewModel.NotesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NotesViewModel notesViewModel;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRecyclerView();

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                adapter.setNotes(notes);
            }
        });
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new NotesAdapter();
        recyclerView.setAdapter(adapter);

    }
}
