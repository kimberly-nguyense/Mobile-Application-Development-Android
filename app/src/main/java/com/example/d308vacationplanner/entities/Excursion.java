package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Excursions")
public class Excursion {
    public Excursion(int excursionID, String excursionName, String excursionDate, int vacationID, String note) {
        this.excursionID = excursionID;
        this.note = note;
        this.vacationID = vacationID;
        this.excursionDate = excursionDate;
        this.excursionName = excursionName;
    }

    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    private String excursionDate;
    private int vacationID;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

}
