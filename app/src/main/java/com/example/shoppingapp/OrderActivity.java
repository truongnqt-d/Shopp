package com.example.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.Interface.IclickItem;
import com.example.shoppingapp.sub_recycleView.ProductAdapter;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        LinearLayout linearLayout_clickBack = findViewById(R.id.back_click);

        linearLayout_clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = this.findViewById(R.id.rcvMenu);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ProductAdapter productAdapter = new ProductAdapter(Constant.cart, this);

        recyclerView.setAdapter(productAdapter);


        Button btn_order = this.findViewById(R.id.order);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, OrderSuccessActivity.class);
                startActivity(intent);
            }
        });
    }
}