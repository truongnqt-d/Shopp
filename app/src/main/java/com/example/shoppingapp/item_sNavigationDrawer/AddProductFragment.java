package com.example.shoppingapp.item_sNavigationDrawer;


import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.dataFirebase.Product;
import com.example.shoppingapp.dataFirebase.ProductSale;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private static final String TAG = "AddProductFragment";
    private EditText txtTitle, txtPrice, txtPercent, txtDescription;
    private RatingBar ratingBar;
    private Button btn_upload;
    private ImageView imgProduct;
    private String idEmail;
    private StorageReference storageRef;
    private ProgressBar progressBar;
    private Uri imageUri;
    private FirebaseFirestore dataProduct;
    private StorageTask uploadTask;
    private Object obj;
    private ProductSale productSale;
    private Product product;

    private String productType;

    ActivityResultLauncher<Intent> activityResultLauncher;

    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi();
        initListener();
    }

    private void initUi() {
        storageRef = FirebaseStorage.getInstance().getReference("product");
        dataProduct = FirebaseFirestore.getInstance();
        progressBar = getView().findViewById(R.id.progress_bar);

        txtTitle = getView().findViewById(R.id.title);
        txtPrice = getView().findViewById(R.id.price);
        txtPercent = getView().findViewById(R.id.percent);
        txtDescription = getView().findViewById(R.id.description);
        ratingBar = getView().findViewById(R.id.ratingBar);
        imgProduct = getView().findViewById(R.id.images);
        btn_upload = getView().findViewById(R.id.btn_upload);
    }

    private void initListener() {

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                            Bundle bundle = result.getData().getExtras();
                            Intent intent = result.getData();
                            imageUri = (Uri) intent.getData();

                            Glide.with(getView()).load(imageUri).error(R.drawable.ic_launcher_profile).into(imgProduct);
                        }
                    }
                });

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadProduct();
            }
        });

        getUserInformationFirebase();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void upLoadProduct() {
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
                                    String id = String.valueOf(System.currentTimeMillis());
                                    if (txtPercent.getText().toString().length() <= 0) {
                                        productType = "products";
                                        product = new Product(id, imageUrl, txtTitle.getText().toString().trim(),
                                                txtDescription.getText().toString().trim(), txtPrice.getText().toString().trim(),
                                                String.valueOf(ratingBar.getRating()));
                                        obj = product;
                                    } else {
                                        productType = "productSales";
                                        productSale = new ProductSale(id, imageUrl, txtTitle.getText().toString().trim(),
                                                txtDescription.getText().toString().trim(), txtPrice.getText().toString().trim(),
                                                String.valueOf(ratingBar.getRating()), txtPercent.getText().toString().trim());
                                        obj = productSale;
                                    }
                                    dataProduct.collection(productType)
                                            .add(obj).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Toast.makeText(getActivity(), "Upload success", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {                                            Toast.makeText(getActivity(), "Upload failed", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getActivity(), "Upload failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserInformationFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idEmail = user.getUid();
        }
    }

}
