package com.example.shoppingapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.dataFirebase.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import customview.DateInputMask;

public class UpdateInformation extends AppCompatActivity {

    private StorageReference storageRef;
    private StorageTask uploadTask;
    private ProgressBar progressBar;
    private Button btnUpdate;
    private ImageView imgPerson;
    private ImageView imgBack;
    private EditText edt_name;
    private EditText edt_birth_date;
    private EditText edt_gender;
    private FirebaseFirestore dataUser;
    private String idEmail;
    private Uri imageUri;
    private DateInputMask dateInputMask;

    private static final String TAG = "EditInformation";
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_information);
        initUi();
        initListener();
    }

    private void initUi() {

        imgPerson = findViewById(R.id.person);
        edt_name = findViewById(R.id.edt_name);
        edt_birth_date = findViewById(R.id.edt_birth_date);
        edt_gender = findViewById(R.id.edt_gender);
        btnUpdate = findViewById(R.id.btn_upload);
        imgBack = findViewById(R.id.back);
//        edt_birth_date.setInputType(InputType.TYPE_CLASS_DATETIME |
//                InputType.TYPE_DATETIME_VARIATION_NORMAL);
        dateInputMask = new DateInputMask(edt_birth_date);

        progressBar = findViewById(R.id.progress_bar);

        storageRef = FirebaseStorage.getInstance().getReference("user");
        dataUser = FirebaseFirestore.getInstance();
    }

    private void initListener() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();
                            Intent intent = result.getData();
                            imageUri = (Uri) intent.getData();

                            Glide.with(UpdateInformation.this).load(imageUri).error(R.drawable.ic_launcher_profile).into(imgPerson);
                        }
                    }
                });
        showUserInformation();

        imgPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edt_name.getText()) || TextUtils.isEmpty(edt_birth_date.getText())
                    || TextUtils.isEmpty(edt_gender.getText())) {
                    Toast.makeText(UpdateInformation.this, "fields cannot be left blank", Toast.LENGTH_SHORT).show();
                } else if (edt_birth_date.getInputType() == (InputType.TYPE_CLASS_DATETIME |
                        InputType.TYPE_DATETIME_VARIATION_NORMAL)){
                    upDateUser();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void showUserInformation() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Users user = (Users) bundle.getSerializable("userData");
        Glide.with(UpdateInformation.this).load(user.getImagePerson()).error(R.drawable.ic_baseline_child_care_24).into(imgPerson);
        edt_name.setText(user.getName());
        edt_birth_date.setText(String.valueOf(user.getAge()));
        edt_gender.setText(user.getGenDer());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void upDateUser() {
        idEmail = FirebaseAuth.getInstance().getUid();
        if (imageUri != null) {
            StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    dataUser.collection("users").document(idEmail)
                                            .update(
                                                    "age", edt_birth_date.getText().toString().trim(),
                                                    "name", edt_name.getText().toString().trim(),
                                                    "genDer", edt_gender.getText().toString().trim(),
                                                    "imagePerson", imageUrl
                                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(UpdateInformation.this, "Update success", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(UpdateInformation.this, "Update failed", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    // Set the "isCapital" field of the city 'DC'
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateInformation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Log.d(TAG, "No update img: ");
            dataUser.collection("users").document(idEmail)
                .update(
                        "age", edt_birth_date.getText().toString().trim(),
                        "name", edt_name.getText().toString().trim(),
                        "genDer", edt_gender.getText().toString().trim()
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateInformation.this, "Update success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateInformation.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

}