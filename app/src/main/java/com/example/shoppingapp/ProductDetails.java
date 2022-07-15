package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.dataFirebase.ProductAddCart;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProductDetails extends AppCompatActivity {
    private Production product;
    private ProductAddCart productionData;
    private ImageView imgProduct;

    private TextView txtTitle, txtPrice, txtEvaluate, txtDescription, txtBuy;
    private RatingBar ratingBar;
    private LinearLayout Layout_add_to_cart;
    private FirebaseFirestore firebaseFirestore;
    private ProductAddCart productAddCart;
    private FirebaseUser idEmail;
    private int quantityData;
    private String idDocumentProduct;
    private boolean flag;

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
        txtDescription = findViewById(R.id.description);
        ratingBar = findViewById(R.id.ratingBar);
        Layout_add_to_cart = findViewById(R.id.add_to_cart);
        firebaseFirestore = FirebaseFirestore.getInstance();
        idEmail = FirebaseAuth.getInstance().getCurrentUser();
        txtBuy = findViewById(R.id.buy);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        product = (Production) bundle.get("object_product");
    }

    private void initListener() {
        getProduct();
        Layout_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataProductFirestore();
            }
        });

        txtBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetails.this, OrderActivity.class));
            }
        });
    }

    private void getDataProductFirestore() {
        firebaseFirestore.collection("cart").whereEqualTo("id", product.getId())
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        productionData = task.getResult().toObjects(ProductAddCart.class);
                        if(product.get)
                        productionData = (ProductAddCart)task.getResult().toObjects(ProductAddCart.class);
                    }
                });
        if(productionData == null) {
            addProductCart();
        } else {
            quantityData = productionData.getQuantity();
            updateQuantityProductCart(quantityData);
        }
    }

    private void addProductCart() {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        productAddCart = new ProductAddCart(product.getId(), product.getTitle(), product.getPrice(),
            product.getDescription(), product.getImgProduct(), product.getRating(), date,
            idEmail.getUid(), 1);

        firebaseFirestore.collection("cart").add(productAddCart)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "onSuccess: ");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: ");
                }
            });
    }

    private void updateQuantityProductCart(int quantity) {
        firebaseFirestore.collection("cart").document(idDocumentProduct)
                .update("quantity", ++ quantity)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void getProduct() {
        Glide.with(this).load(product.getImgProduct()).into(imgProduct);
        txtTitle.setText(product.getTitle());
        txtPrice.setText(product.getPrice());
        String rating = product.getRating() == null ? "3.5" : product.getRating().trim();
        txtEvaluate.setText(String.valueOf(Float.parseFloat(rating)));
        txtDescription.setText(product.getDescription());
        ratingBar.setRating(Float.parseFloat(rating));
    }
}