package com.example.d308vacationplanner.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String vacationName;
    String hotelName;
    String startDate;
    String endDate;
    int vacationID;
    EditText edit_vacationName;
    EditText edit_hotelName;
    TextView edit_startDate;
    TextView edit_endDate;
    Repository repository;

    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
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
            intent.putExtra("vacationID", vacationID);
            startActivity(intent);
        });

        // Set up Recycler View to show excursions associated with vacation
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, repository);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = repository.getAssociatedExcursions(vacationID);
        excursionAdapter.setExcursions(filteredExcursions);

        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        edit_startDate.setOnClickListener(view -> {
            Date date;
            new DatePickerDialog(VacationDetails.this,
                    startDateListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelStartDate();
            }
        };

        edit_endDate.setOnClickListener(view -> {
            Date date;
            new DatePickerDialog(VacationDetails.this,
                    endDateListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEndDate();
            }
        };
    }

    public void updateLabelStartDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        edit_startDate.setText(sdf.format(myCalendar.getTime()));
    }
    public void updateLabelEndDate() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        edit_endDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    public int checkValidDate(){
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        String startDate = edit_startDate.getText().toString();
        String endDate = edit_endDate.getText().toString();

        if(startDate == null || startDate.isEmpty() || endDate == null || endDate.isEmpty()){
            Toast.makeText(VacationDetails.this, "Date fields cannot be empty", Toast.LENGTH_SHORT).show();
            return -1;
        }

        try{
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            if(start.after(end)){
                Toast.makeText(VacationDetails.this, "Error: Start date must be before end date", Toast.LENGTH_SHORT).show();
                return -1;
            }
        } catch (ParseException e) {
            Toast.makeText(VacationDetails.this, "Invalid date format. Please use MM/DD/YYYY", Toast.LENGTH_SHORT).show();
            return -1;
        }
        Toast.makeText(VacationDetails.this, "Saving Vacation", Toast.LENGTH_SHORT).show();
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Vacation vacation;
        if (item.getItemId() == R.id.save_vacation) {
            if (checkValidDate() == -1){
                return true;
            }
            // If new vacation, get next vacation ID and create new vacation from input fields
            else if (vacationID == -1 ) {
                if (repository.getmAllVacations().size() == 0) {
                    vacationID = 1;
                }
                else {
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                }
                vacation = new Vacation(vacationID,
                        edit_vacationName.getText().toString(),
                        edit_hotelName.getText().toString(),
                        edit_startDate.getText().toString(),
                        edit_endDate.getText().toString());
                Toast.makeText(VacationDetails.this, "Adding Vacation", Toast.LENGTH_SHORT).show();
                repository.insert(vacation);
                return true;
            }
            else{
                vacation = new Vacation(vacationID,
                        edit_vacationName.getText().toString(),
                        edit_hotelName.getText().toString(),
                        edit_startDate.getText().toString(),
                        edit_endDate.getText().toString());
                Toast.makeText(VacationDetails.this, "Updating Vacation", Toast.LENGTH_SHORT).show();
                repository.update(vacation);
                return true;
            }
        }
        if (item.getItemId() == R.id.delete_vacation) {
            List<Excursion> associatedExcursions = repository.getAssociatedExcursions(vacationID);
            if(!associatedExcursions.isEmpty()){
                Toast.makeText(VacationDetails.this, "Cannot delete vacation with associated excursions", Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                vacation = new Vacation(vacationID,
                        edit_vacationName.getText().toString(),
                        edit_hotelName.getText().toString(),
                        edit_startDate.getText().toString(),
                        edit_endDate.getText().toString());
                Toast.makeText(VacationDetails.this, "Deleting Vacation", Toast.LENGTH_SHORT).show();
                repository.delete(vacation);
                this.finish();
                return true;
            }
        }
        if (item.getItemId() == R.id.notify_vacation) {
            String dateFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

            String start = edit_startDate.getText().toString();
            String end = edit_endDate.getText().toString();

            Date startDate = null;
            Date endDate = null;
            try{
                startDate = sdf.parse(start);
                endDate = sdf.parse(end);
            } catch (ParseException e) {
                Toast.makeText(VacationDetails.this, "Invalid date format. Please use MM/DD/YYYY", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return true;
            }
            if(startDate == null || endDate == null){
                Toast.makeText(VacationDetails.this, "Invalid date format. Please use MM/DD/YYYY", Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long triggerTime = System.currentTimeMillis() + 60000; // 1 minute from now

                Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, sender);


                Long triggerTimeStart = startDate.getTime();
                Intent intentStart = new Intent(VacationDetails.this, MyReceiver.class);
                intentStart.putExtra("name", vacationName);
                intentStart.putExtra("date", start);
                PendingIntent senderStart = PendingIntent.getBroadcast(VacationDetails.this, ++Main.numAlert, intentStart, PendingIntent.FLAG_IMMUTABLE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTimeStart, senderStart);

                Long triggerTimeEnd = endDate.getTime();
                Intent intentEnd = new Intent(VacationDetails.this, MyReceiver.class);
                intentEnd.putExtra("name", vacationName);
                intentEnd.putExtra("date", end);
                PendingIntent senderEnd = PendingIntent.getBroadcast(VacationDetails.this, ++Main.numAlert, intentEnd, PendingIntent.FLAG_IMMUTABLE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTimeEnd, senderEnd);
                Toast.makeText(VacationDetails.this,
                        vacationName + " notifications set for " + start + " and " + end,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return true;
    }
    @Override
    protected void onResume() {
        // After updating ExcursionDetails, refresh ExcursionDetails
        super.onResume();
        List<Excursion> filteredExcursions = repository.getAssociatedExcursions(vacationID);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, repository);
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(filteredExcursions);
    }
}