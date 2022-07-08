package com.example.shoppingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassWord;
    private Button btnSignup_Login;
    private ProgressDialog progressDialog;
    private FirebaseFirestore dbSignUp = FirebaseFirestore.getInstance();

    private static final String TAG = "SignUpActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_signup);
//        setContentView(R.layout.activity_signup);

        initUi();
        initListener();
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassWord = findViewById(R.id.edt_password);
        btnSignup_Login = findViewById(R.id.btn_signup_login);

        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        btnSignup_Login.setOnClickListener(view -> onClickSignUp());
    }

    private void onClickSignUp() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassWord.getText().toString().trim();

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if(email.matches(regexPattern) == false){
            Toast.makeText(getApplication(), "\n" +
                    "wrong email format", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplication(), "\n" +
                    "Email or password cannot be left blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtPassWord.length() < 6) {
            Toast.makeText(getApplication(), "\n" +
                    "password is 6 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth authSignUp = FirebaseAuth.getInstance();

        progressDialog.show();
        authSignUp.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser userUid = FirebaseAuth.getInstance().getCurrentUser();

                            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                            String date = df.format(Calendar.getInstance().getTime());
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", email);
                            user.put("fullName", "user01");
                            user.put("age", "18");
                            user.put("genDer", "Male");
                            user.put("isAdmin", false);
                            user.put("dayCreate", date);

                            dbSignUp.document("users/" + userUid.getUid())
                                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + userUid.getUid());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });

                            Toast.makeText(SignUpActivity.this, "Sign Up Success",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivityLogin.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Sign Up failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
