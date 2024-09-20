package com.example.d308vacationplanner.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    Repository repository;
    String excursionName;
    String excursionDate;
    String excursionNote;
    int excursionID;
    int vacationID;
    String vacationName;
    String vacationStart;
    String vacationEnd;
    TextView editVacationName;
    EditText editName;
    TextView editDate;
    EditText editNote;

    boolean isExcursionSaved = false;

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
        // Get input fields
        editVacationName = findViewById(R.id.edit_vacationName);
        editName = findViewById(R.id.edit_excursionName);
        editDate = findViewById(R.id.edit_excursionDate);
        editNote = findViewById(R.id.edit_excursionNote);
        // Get passed values
        vacationID = getIntent().getIntExtra("vacationID", -1);
        vacationName = getIntent().getStringExtra("vacationName");
        vacationStart = getIntent().getStringExtra("vacationStart");
        vacationEnd = getIntent().getStringExtra("vacationEnd");

        excursionID = getIntent().getIntExtra("excursionID", -1);
        excursionName = getIntent().getStringExtra("excursionName");
        excursionDate = getIntent().getStringExtra("excursionDate");
        excursionNote = getIntent().getStringExtra("excursionNote");
        // Check if excursion has been saved
        isExcursionSaved = getIntent().getBooleanExtra("isExcursionSaved", false);
        editVacationName.addTextChangedListener(textWatcher);
        editName.addTextChangedListener(textWatcher);
        editDate.addTextChangedListener(textWatcher);
        editNote.addTextChangedListener(textWatcher);
        // Set input fields
        setVacationDetailsWithoutTriggeringTextWatcher();

        if(excursionID != -1){
            isExcursionSaved = true;
        }

        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        editDate.setOnClickListener(view -> {

            String info = editDate.getText().toString();
            if (info.equals("")) {
                info = sdf.format(Calendar.getInstance().getTime());
            }
            try {
                myCalendar.setTime(sdf.parse(info));
            } catch (ParseException e) {
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

    private void setVacationDetailsWithoutTriggeringTextWatcher() {
        editVacationName.removeTextChangedListener(textWatcher);
        editName.removeTextChangedListener(textWatcher);
        editDate.removeTextChangedListener(textWatcher);
        editNote.removeTextChangedListener(textWatcher);

        editVacationName.setText(vacationName);
        editName.setText(excursionName);
        editDate.setText(excursionDate);
        editNote.setText(excursionNote);

        editVacationName.addTextChangedListener(textWatcher);
        editName.addTextChangedListener(textWatcher);
        editDate.addTextChangedListener(textWatcher);
        editNote.addTextChangedListener(textWatcher);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isExcursionSaved = false;
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public int checkValidDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        String excursionDate = editDate.getText().toString();
        String startDate = getIntent().getStringExtra("vacationStart");
        String endDate = getIntent().getStringExtra("vacationEnd");
        if (excursionDate == null || excursionDate.isEmpty() ||
                startDate == null || startDate.isEmpty() ||
                endDate == null || endDate.isEmpty()) {

            Toast.makeText(ExcursionDetails.this, "Date fields cannot be empty", Toast.LENGTH_SHORT).show();
            return -1;
        }

        try {
            Date date = sdf.parse(excursionDate);
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            if (date.before(start) || date.after(end)) {
                Toast.makeText(ExcursionDetails.this, "Error: Excursion must be between Vacation start and end dates.", Toast.LENGTH_SHORT).show();
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
        if (item.getItemId() == R.id.save_excursion) {
            if (checkValidDate() == -1) {
            }
            else if (editName.getText().toString().isEmpty()) {
                Toast.makeText(ExcursionDetails.this, "Excursion name cannot be empty", Toast.LENGTH_SHORT).show();
                return true;
            }
            // If new excursion, get next excursion ID and create new excursion from input fields
            else if (excursionID == -1) {
                if (repository.getmAllExcursions().size() == 0) {
                    excursionID = 1;
                } else {
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                }
                excursion = new Excursion(excursionID,
                        editName.getText().toString(),
                        editDate.getText().toString(), vacationID,
                        editNote.getText().toString());
                Toast.makeText(ExcursionDetails.this, "Adding Excursion", Toast.LENGTH_SHORT).show();
                repository.insert(excursion);
            } else {
                excursion = new Excursion(excursionID,
                        editName.getText().toString(),
                        editDate.getText().toString(), vacationID,
                        editNote.getText().toString()
                );
                Toast.makeText(ExcursionDetails.this, "Updating Excursion", Toast.LENGTH_SHORT).show();
                repository.update(excursion);
            }
            isExcursionSaved = true;
            return true;
        }
        if (item.getItemId() == R.id.delete_excursion) {
            if(isExcursionSaved == false){
                Toast.makeText(ExcursionDetails.this, "Cannot delete excursion that has not been saved", Toast.LENGTH_SHORT).show();
                return true;
            }
            excursion = new Excursion(excursionID,
                    editName.getText().toString(),
                    editDate.getText().toString(), vacationID,
                    editNote.getText().toString()
            );
            Toast.makeText(ExcursionDetails.this, "Deleting Excursion", Toast.LENGTH_SHORT).show();
            repository.delete(excursion);
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.notify_date) {
            if(isExcursionSaved == false){
                Toast.makeText(ExcursionDetails.this, "Cannot notify excursion that has not been saved", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (checkValidDate() == -1) {
                return true;
            }
            String dateFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

            excursionDate = editDate.getText().toString();

            if (excursionDate == null || excursionDate.isEmpty()){
                Toast.makeText(ExcursionDetails.this, "Date fields cannot be empty", Toast.LENGTH_SHORT).show();
                return true;
            }

            Date date = null;
            try {
                date = sdf.parse(excursionDate);
            } catch (ParseException e) {
                Toast.makeText(ExcursionDetails.this, "Invalid date format. Please use MM/DD/YYYY", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return true;
            }

            Long triggerTime = date.getTime();
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            intent.putExtra("name", editName.getText().toString());
            intent.putExtra("date", editDate.getText().toString());

            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++Main.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, sender);
            Toast.makeText(ExcursionDetails.this, editName.getText().toString() + " notification set for " + editDate.getText().toString(), Toast.LENGTH_SHORT).show();
            return true;        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }
}