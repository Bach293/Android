package com.example.login_register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_register.R;
import com.example.login_register.adapter.CartsAdapter;
import com.example.login_register.databinding.ActivityCartBinding;
import com.example.login_register.model.Cart;

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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