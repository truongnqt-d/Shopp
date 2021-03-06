package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.fragment.AddProductCart;
import com.example.shoppingapp.sub_fragment_adapter.Production;

public class ProductDetails extends AppCompatActivity {
    private Production product;
    private ImageView imgProduct;

    private TextView txtTitle, txtPrice, txtEvaluate, txtBuy;
    private RatingBar ratingBar;

    private static final String TAG = "AddToCartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        initUi();
        initListener();
    }

    private void initUi() {
        imgProduct = findViewById(R.id.images);
        txtTitle = findViewById(R.id.title);
        txtPrice = findViewById(R.id.price);
        txtEvaluate = findViewById(R.id.evaluate);
        ratingBar = findViewById(R.id.ratingBar);
        txtBuy = findViewById(R.id.buy);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        product = (Production) bundle.get("object_product");
    }

    private void initListener() {
        getProduct();

        txtBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetails.this, OrderActivity.class));
            }
        });
    }

    public void onClickAddToCart(View view){

        AddProductCart fragment = new AddProductCart().newInstance(product);
        fragment.show(getSupportFragmentManager(), "TAG");
    }

    private void getProduct() {
        Glide.with(this).load(product.getImgProduct()).into(imgProduct);
        txtTitle.setText(product.getTitle());
        txtPrice.setText(product.getPrice());
        String rating = product.getRating() == null ? "3.5" : product.getRating().trim();
        txtEvaluate.setText(String.valueOf(Float.parseFloat(rating)));
        ratingBar.setRating(Float.parseFloat(rating));
    }
}