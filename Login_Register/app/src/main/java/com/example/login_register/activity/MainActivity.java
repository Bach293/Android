package com.example.login_register.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.R;
import com.example.login_register.Repository.ProductRepository;
import com.example.login_register.adapter.ProductsAdapter;
import com.example.login_register.databinding.ActivityMainBinding;
import com.example.login_register.model.Cart;
import com.example.login_register.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ProductRepository productRepository;
    RecyclerView rvProduct;
    private final Cart cart = new Cart();

    private TextView textCartItemCount;
    private static final String KEY_PRODUCT_LIST = "product_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
//        initData();
        initDataFromFile();
//        initProducts(getApplicationContext());
//        loadProducts(getApplicationContext());

        rvProduct = binding.rvproduct;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        rvProduct.setLayoutManager(mLayoutManager);

        ProductsAdapter rvAdapter = new ProductsAdapter(this, ProductRepository.getProductList());
        rvProduct.setAdapter(rvAdapter);
    }

    private void initData() {
        ArrayList<Product> alProduct = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Product p = new Product(i, "ProductName" + i);
            int resID = getResId("ss_" + i % 9, R.drawable.class);
            if (resID == -1) {
                Log.e("initData", "Resource ID not found for ss_" + i % 9);
                continue; // Bỏ qua sản phẩm này nếu không tìm thấy tài nguyên
            }
            Uri imgUri = getUri(resID);
            Log.d("initData", "Product " + i + " - ResID: " + resID + " - Uri: " + imgUri.toString());
            p.setImage(imgUri);
            p.setPrice(Float.parseFloat(String.format("%.2f", new Random().nextFloat() * 1000)));
            alProduct.add(p);
        }
        this.productRepository = new ProductRepository(alProduct);
    }

    private void initDataFromFile() {
        ArrayList<Product> alProduct = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.products);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int resID = getResId(parts[2], R.drawable.class);
                if (resID == -1) {
                    Log.e("initData", "Resource ID not found for" + parts[2]);
                    continue;
                }
                Uri imgUri = getUri(resID);
                float price = Float.parseFloat(parts[3]);

                Product product = new Product(id, name);
                product.setImage(imgUri);
                product.setPrice(price);
                alProduct.add(product);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.productRepository = new ProductRepository(alProduct);
    }

    public void initProducts(Context context) {
        List<Product> productList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            Product p = new Product(i, "ProductName" + i);
            int resID = getResId("ss_" + i % 9, R.drawable.class);
            if (resID == -1) {
                Log.e("initData", "Resource ID not found for" + i % 9);
                continue;
            }
            Uri imgUri = getUri(resID);
            p.setImage(imgUri);
            p.setPrice(Float.parseFloat(String.format("%.2f", random.nextFloat() * 1000)));
            productList.add(p);
        }

        // Chuyển đổi danh sách sản phẩm thành chuỗi JSON
        Gson gson = new Gson();
        String json = gson.toJson(productList);

        // Lưu chuỗi JSON vào Shared Preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PRODUCT_LIST, json);
        editor.apply();
    }

    public void loadProducts(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPreferences.getString(KEY_PRODUCT_LIST, null);
        if (json == null) {
            Log.e("initData", "Json no data");
        }

        // Chuyển đổi chuỗi JSON thành danh sách sản phẩm
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        this.productRepository = new ProductRepository(gson.fromJson(json, type));
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
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