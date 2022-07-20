package com.example.shoppingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateProductActivity extends AppCompatActivity {
    private static final String TAG = "UpdateProductActivity";
    private Production product;
    private ImageView imgProduct;
    private EditText edtTitle, edtPrice, edtPercent, edtDescription;
    private RatingBar ratingBar;
    private FirebaseFirestore getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        initUi();
        initListener();
    }

    private void initUi() {
        imgProduct = findViewById(R.id.images);
        edtTitle = findViewById(R.id.title);
        edtPrice = findViewById(R.id.price);
        ratingBar = findViewById(R.id.ratingBar);
    }

    private void initListener() {
        getProduct();
    }

    private void getProduct() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        product = (Production) bundle.get("object_product");
        Glide.with(this).load(product.getImgProduct()).into(imgProduct);
//        Glide.with(this).load(product.getImgProduct()).into(imgProduct);
        edtTitle.setText(product.getTitle());
        edtPrice.setText(product.getPrice());
        String rating = product.getRating() == null ? "3.5" : product.getRating().trim();
        ratingBar.setRating(Float.parseFloat(rating));

        getData = FirebaseFirestore.getInstance();
        if(edtPercent.getText().toString().trim().length() <= 0) {
            edtPercent.setVisibility(View.INVISIBLE);
            updateProduct();
        }

        updateProductSale();
    }

    private void updateProductSale() {
        Log.d(TAG, "getProduct: " + product.getId());
    }

    private void updateProduct() {
        Log.d(TAG, "getProduct: " + product.getId());
    }
}