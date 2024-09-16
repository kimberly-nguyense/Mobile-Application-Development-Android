package com.example.d308vacationplanner.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExcursionDetails extends AppCompatActivity {
    Repository repository;
    String name;
    String date;
    String note;
    int excursionID;
    int vacationID;
    String vacationStart;
    String vacationEnd;
    EditText editName;
    TextView editDate;
    EditText editNote;

    DatePickerDialog.OnDateSetListener dateListener;
    final Calendar myCalendar = Calendar.getInstance();

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

        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        editDate.setOnClickListener(view -> {

            Date date;
            String info = editDate.getText().toString();
            if(info.equals("")){
                info = sdf.format(Calendar.getInstance().getTime());
            }
            try{
                myCalendar.setTime(sdf.parse(info));
            }
            catch (ParseException e){
                e.printStackTrace();
            }
            new DatePickerDialog(ExcursionDetails.this,
                    dateListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelDate();
            }
        };
    }

    public void updateLabelDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public int checkValidDate(){
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        String excursionDate = editDate.getText().toString();
        String startDate = vacationStart;
        String endDate = vacationEnd;

        try{
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            if(start.after(end)){
                Toast.makeText(ExcursionDetails.this, "Error: Start date must be before end date", Toast.LENGTH_SHORT).show();
                return -1;
            }
        } catch (ParseException e) {
            Toast.makeText(ExcursionDetails.this, "Invalid date format. Please use MM/DD/YYYY", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Excursion excursion;
        repository = new Repository(getApplication());
        if (item.getItemId() == R.id.share_details) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString()
                    /* + "Excursion Name: " + name + "Excursion Date: \" + date"*/);
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
            if (checkValidDate() == -1){
            }
            // If new excursion, get next excursion ID and create new excursion from input fields
            else if (excursionID == -1) {
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