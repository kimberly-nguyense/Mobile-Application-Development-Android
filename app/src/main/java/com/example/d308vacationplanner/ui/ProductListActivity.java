package com.example.d308vacationplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.Part;
import com.example.d308vacationplanner.entities.Product;
import com.example.d308vacationplanner.database.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductListActivity extends AppCompatActivity {
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton_addProduct);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
            startActivity(intent);
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_sample_data) {
            Toast.makeText(ProductListActivity.this, "Put in Sample Data", Toast.LENGTH_SHORT).show();

            repository = new Repository(getApplication());

            Product product = new Product(0,"bicycle",100.0);
            repository.insert(product);
            product = new Product(0,"tricycle",100.0);
            repository.insert(product);
            Part part = new Part(0,"wheel",7.89,1);
            repository.insert(part);
            part = new Part(0,"pedal",5.67,1);
            repository.insert(part);

            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }

}