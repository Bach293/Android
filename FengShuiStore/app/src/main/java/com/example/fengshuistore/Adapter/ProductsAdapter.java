package com.example.fengshuistore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fengshuistore.Activity.CartActivity;
import com.example.fengshuistore.Model.Cart;
import com.example.fengshuistore.Model.Product;
import com.example.fengshuistore.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private ArrayList<Product> products ;
    public Cart cart = new Cart();

    private Context mContext;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  Product p = products.get(position);
        String sProductName = p.getName();
        String sProductDescription = p.getDescription();
        holder.txtProductName.setText(sProductName);
        holder.txtProductDescription.setText(sProductDescription);
        holder.txtPrice.setText(""+p.getPrice());
        holder.idIVSSImage.setImageURI(p.getImage());
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonClick(view, p);
            }
        });

    }

    private void addButtonClick(View view, Product p) {
        cart.addCart(p);
        showSnackbar(view, mContext.getString(R.string.add_product) + p.getName(), Snackbar.LENGTH_SHORT);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtProductName;
        public TextView txtProductDescription;
        public TextView txtPrice;
        public RelativeLayout relativeLayout;

        public ImageView ivAdd;
        public ImageView idIVSSImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtProductName = (TextView)itemView.findViewById(R.id.idTVName);
            this.txtProductDescription = (TextView)itemView.findViewById(R.id.idTVDescription);
            this.txtPrice = (TextView)itemView.findViewById(R.id.idTVPrice);
            this.ivAdd = (ImageView)itemView.findViewById(R.id.ivAdd);
            this.idIVSSImage = (ImageView)itemView.findViewById(R.id.idIVSSImage);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout);

        }
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).setAction("Cart", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CartActivity.class);
                mContext.startActivity(intent);
            }
        }).show();

    }

}
