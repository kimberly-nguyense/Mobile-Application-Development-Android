package com.example.d308vacationplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d308vacationplanner.dao.PartDAO;
import com.example.d308vacationplanner.dao.ProductDAO;
import com.example.d308vacationplanner.entities.Part;
import com.example.d308vacationplanner.entities.Product;

@Database(entities = {Product.class, Part.class}, version = 3, exportSchema = false)
public abstract class BicycleDatabaseBuilder extends RoomDatabase {
    public abstract ProductDAO productDAO();
    public abstract PartDAO partDAO();
    public static BicycleDatabaseBuilder INSTANCE;

    static BicycleDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (BicycleDatabaseBuilder.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BicycleDatabaseBuilder.class, "MyBicycleDatabase.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}