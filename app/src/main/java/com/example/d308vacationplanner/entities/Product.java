package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Products")
public class Product {
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Product(int productID, String productName, double price) {
        this.price = price;
        this.productName = productName;
        this.productID = productID;
    }

    @PrimaryKey(autoGenerate = true)
    private  int productID;
    private String productName;
    private double price;


}
