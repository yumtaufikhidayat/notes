package com.taufik.notes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.taufik.notes.R;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.taufik.notes.activity.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.taufik.notes.activity.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.taufik.notes.activity.EXTRA_PRIORITY";

    EditText etTitle, etDescription;
    NumberPicker nbPriority;
    LinearLayout llSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        setInit();

        nbPriority.setMinValue(1);
        nbPriority.setMaxValue(10);

        setGetSupportActionBar();

        setLlSaveOnClickListener();
    }

    private void saveNote() {

        String noteTitle = etTitle.getText().toString();
        String noteDescription = etDescription.getText().toString();
        int notePriority = nbPriority.getValue();

        if (noteTitle.trim().isEmpty() || noteDescription.trim().isEmpty()) {
            Toast.makeText(this, R.string.tvAllFieldsRequired, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, noteTitle);
        data.putExtra(EXTRA_DESCRIPTION, noteDescription);
        data.putExtra(EXTRA_PRIORITY, notePriority);

        setResult(RESULT_OK, data);
        finish();
    }

    private void setLlSaveOnClickListener() {
        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void setGetSupportActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            getSupportActionBar().setTitle(R.string.tvAddNote);
        }
    }

    private void setInit() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        nbPriority = findViewById(R.id.nbPriority);
        llSave = findViewById(R.id.llSave);
    }
}
