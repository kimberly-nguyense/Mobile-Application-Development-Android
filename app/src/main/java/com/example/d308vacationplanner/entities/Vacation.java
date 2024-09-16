package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Vacations")
public class Vacation {
    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String VacationName) {
        this.vacationName = VacationName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getvacationID() {
        return vacationID;
    }

    public void setvacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public Vacation(int vacationID, String vacationName, String hotelName, String startDate, String endDate) {
        this.hotelName = hotelName;
        this.vacationName = vacationName;
        this.vacationID = vacationID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private String hotelName;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private String startDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private String endDate;


    public int getVacationID() {
        return vacationID;
    }

}
