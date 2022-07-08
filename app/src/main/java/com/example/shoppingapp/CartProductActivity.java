package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.dataFirebase.ProductAddCart;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.example.shoppingapp.sub_recycleView.ProductAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CartProductActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout_clickBack;
    private Button buttonBuy;
    private FirebaseFirestore firebaseFirestore;
    private LinearLayoutManager linearLayoutManager;

    private static final String TAG = "CartProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_product);

        initUi();
        initListener();
    }

    private void initUi() {
        firebaseFirestore = FirebaseFirestore.getInstance();

        linearLayout_clickBack = findViewById(R.id.back_click);
        recyclerView = this.findViewById(R.id.rcvMenu);
        buttonBuy = findViewById(R.id.buy);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductAdapter(showCartProduct(), this);
        recyclerView.setAdapter(productAdapter);

    }

    private void initListener() {

        linearLayout_clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartProductActivity.this, OrderActivity.class);
                Constant.buy.add(productAdapter);
                startActivity(intent);
            }
        });

    }

    private List<ProductAddCart> showCartProduct() {
        List<ProductAddCart> listProduct = new ArrayList<>();

        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore.collection("cart").whereEqualTo("idUser", userId.getUid())
            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                        listProduct.add(queryDocumentSnapshot.toObject(ProductAddCart.class));
                    }
                    if(listProduct.size() > 0) {
                        productAdapter.addData(listProduct);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        return listProduct;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(productAdapter != null){
            productAdapter.release();
        }

        if(productAdapter != null) {
            productAdapter.release();
        }
    }
}