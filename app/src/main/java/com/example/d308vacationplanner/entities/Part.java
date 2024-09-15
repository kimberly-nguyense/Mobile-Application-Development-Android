package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Parts")
public class Part {
    @PrimaryKey(autoGenerate = true)
    private int partID;
    private String partName;

    public Part(int partID, String partName, double price, int productID ) {
        this.productID = productID;
        this.price = price;
        this.partName = partName;
        this.partID = partID;
    }

    private double price;
    private int productID;

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
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

}
