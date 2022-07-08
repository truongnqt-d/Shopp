//package com.example.shoppingapp.login_with;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.example.shoppingapp.MainActivityLogin;
//import com.example.shoppingapp.ProductActivity;
//import com.example.shoppingapp.R;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class GoogleLogin extends MainActivityLogin {
//    private GoogleSignInClient mGoogleSignInClient;
//    private static final int RC_SIGN_IN = 2;
//    private static final String TAG = "MainActivityLogin";
//
//    private FirebaseAuth auth;
//    private FirebaseUser user;
//    private FirebaseFirestore firestoreSetAccount;
//    private String idEmail;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        connection();
//    }
//
//    private void connection() {
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getResources().getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//
//        Map<String, Object> userId = new HashMap<>();
//        userId.put("id", System.currentTimeMillis());
//        userId.put("fullName", "user01");
//        userId.put("age", "18");
//        userId.put("genDer", "Male");
//        userId.put("isAdmin", false);
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("users")
//                .add(userId)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//
//
//                Toast.makeText(getApplication(), "\n" +
//                        "Sign in with Google", Toast.LENGTH_SHORT).show();
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//
//                Toast.makeText(getApplication(), "\n" +
//                        "Google sign in failed", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    public void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
////                            Log.d(TAG, "signInWithCredential:success");
//                            Toast.makeText(getApplication(), "\n" +
//                                    "sign in Google :success", Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = auth.getCurrentUser();
//
//
//                            updateUI(user);
//
//                        } else {
//                            // If sign in fails, display a message to the user.
////                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//
//                            Toast.makeText(getApplication(), "\n" +
//                                    "sign in with Google:false", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
//
//    private void updateUI(FirebaseUser user) {
//        Intent intent = new Intent(this, ProductActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//
//        finishAffinity();
//    }
//}
