package com.example.shoppingapp.credit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.MainActivityLogin;
import com.example.shoppingapp.ProductActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.dataFirebase.Credit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCredit extends AppCompatActivity {
    private LinearLayout layout;
    private Button btnDone;
    private TextView txtCartNumber, txtExpirationDate, txtVerificationCodes, txtCardHolder, txtAddress, txtPostalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit);

        initUi();
        initListener();
    }

    private void initUi() {
        layout = findViewById(R.id.title);
        btnDone = findViewById(R.id.done);

        txtCartNumber = findViewById(R.id.edt_sothe);
        txtExpirationDate = findViewById(R.id.edt_date);
        txtVerificationCodes = findViewById(R.id.edt_maxacthu);
        txtCardHolder = findViewById(R.id.edt_name);
        txtAddress = findViewById(R.id.edt_address);
        txtPostalCode = findViewById(R.id.edt_mabuuchinh);
    }

    private void initListener() {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCredit();
            }
        });
    }

    private void addCredit() {
        FirebaseFirestore addCredit = FirebaseFirestore.getInstance();

        FirebaseUser idUser = FirebaseAuth.getInstance().getCurrentUser();

        Credit credit = new Credit(txtCartNumber.getText().toString().trim(),
                txtExpirationDate.getText().toString().trim(), txtVerificationCodes.getText().toString().trim(),
                txtCardHolder.getText().toString().trim(), txtAddress.getText().toString().trim(),
                txtPostalCode.getText().toString().trim(), idUser.getUid());
        addCredit.collection("credit").add(credit)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddCredit.this, "add On Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCredit.this, "add On Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
