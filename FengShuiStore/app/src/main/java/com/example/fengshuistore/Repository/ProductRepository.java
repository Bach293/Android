package com.example.fengshuistore.Repository;

import android.content.Context;

import com.example.fengshuistore.Model.Product;
import com.example.fengshuistore.SqliteOpenHelper.SqliteOpenHelper;

import java.util.ArrayList;

public class ProductRepository {
    private SqliteOpenHelper dbHelper;
    private static ArrayList<Product> productList = new ArrayList<>();
    public ProductRepository(Context context) {
        dbHelper = new SqliteOpenHelper(context);
        if (productList.isEmpty()) {
            productList = dbHelper.getAllProducts(); // Load from DB if the list is empty
        }
    }
    public ProductRepository(){

    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public static void setProductList(ArrayList<Product> productList) {
        ProductRepository.productList = productList;
    }

    public void addProduct(Product p){
        dbHelper.insertProduct(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getImage());
        productList.add(p);
    }

    public Product getProduct(Integer id){
        for ( Product p : productList) {
            if (id == p.getId())
                return p;
        }
        return null;
    }

}
