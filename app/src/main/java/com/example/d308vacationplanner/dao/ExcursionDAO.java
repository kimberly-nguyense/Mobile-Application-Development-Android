package com.example.d308vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationplanner.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM Excursions ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM Excursions WHERE vacationID = :prod ORDER BY excursionID ASC")
    List<Excursion> getAssociatedExcursions(int prod);

    @Query("DELETE FROM Excursions")
    void deleteAll();

    @Query("DELETE FROM sqlite_sequence WHERE name = 'Excursions'")
    void resetExcursionIdGenerator();
}
