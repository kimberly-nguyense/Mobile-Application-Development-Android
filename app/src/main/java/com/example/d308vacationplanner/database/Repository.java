package com.example.d308vacationplanner.database;

import android.app.Application;

import com.example.d308vacationplanner.dao.ExcursionDAO;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final VacationDAO mVacationDAO;
    private final ExcursionDAO mExcursionDAO;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mVacationDAO = db.VacationDAO();
        mExcursionDAO = db.ExcursionDAO();
    }

    public List<Vacation> getmAllVacations(){
        databaseWriteExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllVacations;
    }

    public Vacation getVacation(int vacationID) {
        return mVacationDAO.getVacation(vacationID);
    }

    public long insert(Vacation vacation){
        final long[] vacationID = new long[1];
        databaseWriteExecutor.execute(() -> {
            vacationID[0] = mVacationDAO.insert(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return vacationID[0];
    }

    public void update(Vacation vacation){
        databaseWriteExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation){
        databaseWriteExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Excursion> getmAllExcursions(){
        databaseWriteExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public List<Excursion> getAssociatedExcursions(int vacationID){
        databaseWriteExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAssociatedExcursions(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    public void insert(Excursion excursion){
        databaseWriteExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion){
        databaseWriteExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion){
        databaseWriteExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        databaseWriteExecutor.execute(() -> {
            mVacationDAO.deleteAll();
            mExcursionDAO.deleteAll();

            mVacationDAO.resetVacationIdGenerator();
            mExcursionDAO.resetExcursionIdGenerator();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
