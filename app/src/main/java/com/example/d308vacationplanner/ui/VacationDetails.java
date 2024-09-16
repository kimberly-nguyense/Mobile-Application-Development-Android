package com.example.d308vacationplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationDetails extends AppCompatActivity {
    String vacationName;
    String hotelName;
    String startDate;
    String endDate;
    int vacationID;
    EditText edit_vacationName;
    EditText edit_hotelName;
    EditText edit_startDate;
    EditText edit_endDate;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get input fields
        edit_vacationName = findViewById(R.id.edit_vacationName);
        edit_hotelName = findViewById(R.id.edit_hotelName);
        edit_startDate = findViewById(R.id.edit_startDate);
        edit_endDate = findViewById(R.id.edit_endDate);
        // Get clicked vacation details
        vacationName = getIntent().getStringExtra("vacationName");
        hotelName = getIntent().getStringExtra("vacationHotelName");
        startDate = getIntent().getStringExtra("vacationStartDate");
        endDate = getIntent().getStringExtra("vacationEndDate");
        vacationID = getIntent().getIntExtra("vacationID", -1);
        // Set input fields
        edit_vacationName.setText(vacationName);
        edit_hotelName.setText(hotelName);
        edit_startDate.setText(startDate);
        edit_endDate.setText(endDate);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton_addExcursion);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            startActivity(intent);
        });

        // Set up Recycler View to show excursions associated with vacation
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = repository.getAssociatedExcursions(vacationID);
        excursionAdapter.setExcursions(filteredExcursions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Vacation vacation;
        if (item.getItemId() == R.id.save_vacation) {
            // If new vacation, get next vacation ID and create new vacation from input fields
            if (vacationID == -1) {
                if (repository.getmAllVacations().size() == 0) {
                    vacationID = 1;
                }
                else {
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                    vacation = new Vacation(vacationID,
                            edit_vacationName.getText().toString(),
                            edit_hotelName.getText().toString(),
                            edit_startDate.getText().toString(),
                            edit_endDate.getText().toString());
                    repository.insert(vacation);
                    this.finish();
                }
            }
            else{
                vacation = new Vacation(vacationID,
                        edit_vacationName.getText().toString(),
                        edit_hotelName.getText().toString(),
                        edit_startDate.getText().toString(),
                        edit_endDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
        }
        if (item.getItemId() == R.id.delete_vacation) {
            vacation = new Vacation(vacationID,
                    edit_vacationName.getText().toString(),
                    edit_hotelName.getText().toString(),
                    edit_startDate.getText().toString(),
                    edit_endDate.getText().toString());
            repository.delete(vacation);
            this.finish();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }
}