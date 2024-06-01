package com.example.fengshuistore.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fengshuistore.Adapter.ProductsAdapter;
import com.example.fengshuistore.Model.Product;
import com.example.fengshuistore.R;
import com.example.fengshuistore.Repository.ProductRepository;
import com.example.fengshuistore.databinding.ActivityMainBinding;

import java.lang.reflect.Field;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ProductRepository productRepository;
    private RecyclerView rvProduct;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        productRepository = new ProductRepository(this);
        initData();

        rvProduct = binding.rvproduct;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(mLayoutManager);

        ProductsAdapter rvAdapter = new ProductsAdapter(this, ProductRepository.getProductList());
        rvProduct.setAdapter(rvAdapter);
    }

    private void initData() {
        int j = 0;
        for (int i = 0; i < 100; i++) {
            if (j == 10) {
                j = 0;
            }
            Product p = new Product();
            String name = "FlowerName" + i;
            String description = "FlowerDescription" + i;
            int resID = getResId("ss_" + j, R.drawable.class);
            j++;
            if (resID == -1) {
                Log.e("initData", "Resource ID not found for ss_" + j);
                continue;
            }
            Uri imgUri = getUri(resID);
            Log.d("initData", "Flower " + i + " - ResID: " + resID + " - Uri: " + imgUri.toString());
            p.setId(i);
            p.setName(name);
            p.setDescription(description);
            p.setImage(imgUri);
            p.setPrice(Float.parseFloat(String.format("%.2f", new Random().nextFloat() * 1000)));
            productRepository.addProduct(p);
        }
    }

    public Uri getUri(int resId) {
        return Uri.parse("android.resource://" + this.getPackageName() + "/" + resId);
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mnu_cart) {
            Log.d("this", "cart show here");
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
        }
        if (id == R.id.bntLogout) {
            Log.d("this", "logout");
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}