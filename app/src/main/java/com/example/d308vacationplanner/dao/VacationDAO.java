package com.example.d308vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM Vacations ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM Vacations WHERE vacationID = :vacationID")
    Vacation getVacation(int vacationID);

    @Query("DELETE FROM Vacations")
    void deleteAll();

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Vacations'")
    void resetVacationIdGenerator();
}
