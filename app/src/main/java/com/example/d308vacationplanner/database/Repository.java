package com.example.d308vacationplanner.database;

import android.app.Application;

import com.example.d308vacationplanner.dao.PartDAO;
import com.example.d308vacationplanner.dao.ProductDAO;
import com.example.d308vacationplanner.entities.Part;
import com.example.d308vacationplanner.entities.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final ProductDAO mProductDAO;
    private final PartDAO mPartDAO;

    private List<Product> mAllProducts;
    private List<Part> mAllParts;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        BicycleDatabaseBuilder db = BicycleDatabaseBuilder.getDatabase(application);
        mProductDAO = db.productDAO();
        mPartDAO = db.partDAO();
    }

    public List<Product> getmAllProducts(){
        databaseWriteExecutor.execute(() -> {
            mAllProducts = mProductDAO.getAllProducts();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllProducts;
    }

    public void insert(Product product){
        databaseWriteExecutor.execute(() -> {
            mProductDAO.insert(product);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Product product){
        databaseWriteExecutor.execute(() -> {
            mProductDAO.update(product);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Product product){
        databaseWriteExecutor.execute(() -> {
            mProductDAO.delete(product);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Part> getmAllParts(){
        databaseWriteExecutor.execute(() -> {
            mAllParts = mPartDAO.getAllParts();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllParts;
    }

    public List<Part> getAssociatedParts(int productID){
        databaseWriteExecutor.execute(() -> {
            mAllParts = mPartDAO.getAssociatedParts(productID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return mAllParts;
    }

    public void insert(Part part){
        databaseWriteExecutor.execute(() -> {
            mPartDAO.insert(part);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void update(Part part){
        databaseWriteExecutor.execute(() -> {
            mPartDAO.update(part);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Part part){
        databaseWriteExecutor.execute(() -> {
            mPartDAO.delete(part);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
