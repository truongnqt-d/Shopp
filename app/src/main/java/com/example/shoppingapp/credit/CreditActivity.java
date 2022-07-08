package com.example.shoppingapp.credit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shoppingapp.R;
import com.example.shoppingapp.dataFirebase.Credit;
import com.example.shoppingapp.sub_recycleView.ProductAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CreditActivity extends AppCompatActivity {
    private RecyclerView rcvData;
    private CreditAdapter creditAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit);

        initUi();
        initListener();
    }

    private void initUi() {
        rcvData = findViewById(R.id.rcvData);

        linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);

        creditAdapter = new CreditAdapter(showCredit(), this);
        rcvData.setAdapter(creditAdapter);
    }

    private void initListener() {
    }

    private List<Credit> showCredit() {
        List<Credit> creditList = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore.collection("credit").whereEqualTo("idUser", userId.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                            creditList.add(queryDocumentSnapshot.toObject(Credit.class));
                        }
                        if(creditList.size() > 0) {
                            creditAdapter.addData(creditList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return creditList;
    }
}