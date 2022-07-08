package com.example.shoppingapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.UpdateInformation;
import com.example.shoppingapp.MainActivityLogin;
import com.example.shoppingapp.ProductActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.credit.AddCredit;
import com.example.shoppingapp.credit.CreditActivity;
import com.example.shoppingapp.dataFirebase.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private ProductActivity product;
    private RelativeLayout layout_my_product;
    private TextView txtLogOut;
    private ImageView imagePerson, uploadInformation;
    private TextView txt_name, txt_age, txt_gender;
    private String name, idEmail;
    private Uri imageFirebaseUri;
    private DocumentReference getUserPerSon;
    private Users users;
    private LinearLayout layout_add_credit, layout_credit;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructo
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi();
        initListener();
    }

    private void initUi() {
        product = (ProductActivity) getActivity();
        layout_my_product = getView().findViewById(R.id.layout_my_product);
        layout_credit = getView().findViewById(R.id.layout_credit);
        layout_add_credit = getView().findViewById(R.id.layout_add_credit);
        txtLogOut = getView().findViewById(R.id.log_out);
        imagePerson = getView().findViewById(R.id.person);
        uploadInformation = getView().findViewById(R.id.upload_information);
        txt_name = getView().findViewById(R.id.name);
    }

    private void initListener() {
        getUsers();
        getUserInformationFirebase();
        showUserInformation();

        txtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        uploadInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(product, UpdateInformation.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("put_information_user", users);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        layout_add_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(product, AddCredit.class);
                startActivity(intent);
            }
        });

        layout_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(product, CreditActivity.class));
            }
        });
    }

    private void getUsers() {
//        if(model.getUsers().getIsAdmin() == false){
//            layout_my_product.setVisibility(View.GONE);
//        } else {
//            layout_my_product.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(product , MyProductionList.class);
//                    startActivity(intent);
//                }
//            });
//        }
    }
//    private void getIsAdmin() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        DocumentReference getUserPerSon = FirebaseFirestore.getInstance().document("users/" + user.getUid());
//        getUserPerSon.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "get success isAdmin: " + document.get("isAdmin"));
//                        boolean isAdmin = Boolean.valueOf(String.valueOf(document.get("isAdmin")));
//                        if(isAdmin == false){
//                            layout_my_product.setVisibility(View.GONE);
//                        } else {
//                            layout_my_product.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent(product , MyProductionList.class);
//                                    startActivity(intent);
//                                }
//                            });
//                        }
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//    }


    private void getUserInformationFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idEmail = user.getUid();
            name = user.getDisplayName();
            imageFirebaseUri = user.getPhotoUrl();
        }
    }

    private void showUserInformation() {
        getUserPerSon = FirebaseFirestore.getInstance().document("users/" + idEmail);
        getUserPerSon.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        users = document.toObject(Users.class);

                        Glide.with(product).load(users.getImagePerson()).error(R.drawable.ic_baseline_child_care_24).into(imagePerson);

                        txt_name.setText(users.getFullName());
                    } else {
                        Glide.with(product).load(imageFirebaseUri).error(R.drawable.ic_baseline_child_care_24).into(imagePerson);

                        txt_name.setText(name);

                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        getUserPerSon.get().addOnFailureListener(e -> {

            Glide.with(product).load(imageFirebaseUri).error(R.drawable.ic_baseline_child_care_24).into(imagePerson);

            txt_name.setText(name);
            if(users == null) {
                String img = String.valueOf(imageFirebaseUri) == null ? "imgNull" : String.valueOf(imageFirebaseUri);
                if(img.equals("imgNull")) {
                    users = new Users(name, "", "", String.valueOf(R.drawable.ic_baseline_child_care_24));
                } else {
                    users = new Users(name, "", "", String.valueOf(imageFirebaseUri));
                }
            }
        });
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(product, MainActivityLogin.class));
        product.finish();
    }
}