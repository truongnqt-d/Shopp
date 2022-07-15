package com.example.shoppingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingapp.dataFirebase.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivityLogin extends AppCompatActivity {
    private Button btnSignup;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtPassWord;
    private ProgressDialog progressDialog;
    private FirebaseAuth authCheckLogin;
    private FirebaseUser firebaseUser;
    private Users user;
    private Bundle bundleUser;
    private Intent intent;

    private static final String TAG = "MainActivityLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        initUi();
        initListener();
    }

    private void initUi() {
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtEmail = findViewById(R.id.edt_email);
        edtPassWord = findViewById(R.id.edt_password);

        progressDialog = new ProgressDialog(this);
        authCheckLogin = FirebaseAuth.getInstance();
    }

    private void initListener() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
    }

    private void onClickSignUp() {
        Intent intent = new Intent(MainActivityLogin.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void onClickLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassWord.getText().toString().trim();
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if(email.matches(regexPattern) == false){
            Toast.makeText(getApplication(), "\n" +
                    "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplication(), "\n" +
                    "Email or password cannot be left blank", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        authCheckLogin.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            if(firebaseUser != null) {
                                getUser();
                                progressDialog.dismiss();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivityLogin.this, "Incorrect email or password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getUser() {
        DocumentReference getUserPerSon = FirebaseFirestore.getInstance().document("users/" + firebaseUser.getUid());
//        getUserPerSon.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "get success: ");
//                        user = task.getResult().toObject(Users.class);
//                        bundleUser = new Bundle();
//                        intent = new Intent(MainActivityLogin.this, ProductActivity.class);
//                        bundleUser.putSerializable("userLogin", user);
//
//                        // Sign in success, update UI with the signed-in user's information
//                        intent.putExtras(bundleUser);
//                        startActivity(intent);
//                        finishAffinity();
////                        Constant.user = task.getResult().toObject(Users.class);
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

        getUserPerSon.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    Log.d(TAG, "get success: ");
                    user = documentSnapshot.toObject(Users.class);
                    bundleUser = new Bundle();
                    intent = new Intent(MainActivityLogin.this, ProductActivity.class);
                    bundleUser.putSerializable("userLogin", user);

                    // Sign in success, update UI with the signed-in user's information
                    intent.putExtras(bundleUser);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });
    }
}