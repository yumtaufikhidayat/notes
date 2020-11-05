package com.taufik.notes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.taufik.notes.R;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.taufik.notes.activity.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.taufik.notes.activity.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.taufik.notes.activity.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.taufik.notes.activity.EXTRA_PRIORITY";

    EditText etTitle, etDescription;
    NumberPicker nbPriority;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        setInit();

        setNumberPickerPriority();

        setGetSupportActionBar();

        setbtnSaveOnClickListener();
    }

    private void setNumberPickerPriority() {
        nbPriority.setMinValue(1);
        nbPriority.setMaxValue(3);
    }

    private void setbtnSaveOnClickListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
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

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    private void setGetSupportActionBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

            Intent intent = getIntent();

            if (intent.hasExtra(EXTRA_ID)) {
                setTitle(R.string.tvEditNote);
                etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
                etDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
                nbPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

            } else {
                getSupportActionBar().setTitle(R.string.tvAddNote);
            }

        }
    }

    private void setInit() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        nbPriority = findViewById(R.id.nbPriority);
        btnSave = findViewById(R.id.btnSave);
    }
}
