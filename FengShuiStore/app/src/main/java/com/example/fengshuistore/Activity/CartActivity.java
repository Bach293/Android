package com.example.fengshuistore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fengshuistore.Adapter.CartsAdapter;
import com.example.fengshuistore.Model.Cart;
import com.example.fengshuistore.R;
import com.example.fengshuistore.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvProduct;
    private TextView tvTotal;
    private final Cart cart = new Cart();
    private ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        tvTotal = binding.tvTotal;

        rvProduct = binding.rvproduct;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(mLayoutManager);

        CartsAdapter rvAdapter = new CartsAdapter(this, this.cart);
        rvProduct.setAdapter(rvAdapter);
        tvTotal.setText("" + this.cart.getTotalPrice());
    }
    public void updateData() {

        tvTotal.setText("" + this.cart.getTotalPrice());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnu_main) {
            Log.d("this", "home show here");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (id == R.id.bntLogout) {
            Log.d("this", "logout");
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}