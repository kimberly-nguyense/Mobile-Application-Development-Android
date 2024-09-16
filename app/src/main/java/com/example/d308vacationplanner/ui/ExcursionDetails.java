package com.example.d308vacationplanner.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

public class ExcursionDetails extends AppCompatActivity {
    Repository repository;
    String name;
    String date;
    String note;
    int excursionID;
    int vacationID;
    EditText editName;
    EditText editDate;
    EditText editNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("excursionName");
        editName = findViewById(R.id.edit_excursionName);
        editName.setText(name);
        date = getIntent().getStringExtra("excursionDate");
        editDate = findViewById(R.id.edit_excursionDate);
        editDate.setText(date);
        excursionID = getIntent().getIntExtra("excursionID", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        note = getIntent().getStringExtra("excursionNote");
        editNote = findViewById(R.id.edit_excursionNote);
        editNote.setText(note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Excursion excursion;
        repository = new Repository(getApplication());
        if (item.getItemId() == R.id.share_details) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString() + "Excursion Name: " + name + "Excursion Date: \" + date");
            intent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(intent, null);
            startActivity(shareIntent);
        }
        if (item.getItemId() == R.id.notify_date) {

        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.save_excursion) {
            // If new excursion, get next excursion ID and create new excursion from input fields
            if (excursionID == -1) {
                if (repository.getmAllExcursions().size() == 0) {
                    excursionID = 1;
                } else {
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                    excursion = new Excursion(excursionID,
                            editName.getText().toString(),
                            editDate.getText().toString(), vacationID,
                            editNote.getText().toString()
                    );
                    repository.insert(excursion);
                    this.finish();
                }
            } else {
                excursion = new Excursion(excursionID,
                        editName.getText().toString(),
                        editDate.getText().toString(), vacationID,
                        editNote.getText().toString()
                );
                repository.update(excursion);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.delete_excursion) {
            excursion = new Excursion(excursionID,
                    editName.getText().toString(),
                    editDate.getText().toString(), vacationID,
                    editNote.getText().toString()
            );
            repository.delete(excursion);
            this.finish();
        }

        return true;
    }
}