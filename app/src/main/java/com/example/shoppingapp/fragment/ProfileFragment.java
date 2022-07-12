package com.example.shoppingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.shoppingapp.Constant;
import com.example.shoppingapp.MyProductionList;
import com.example.shoppingapp.UpdateInformation;
import com.example.shoppingapp.MainActivityLogin;
import com.example.shoppingapp.ProductActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.credit.AddCredit;
import com.example.shoppingapp.credit.CreditActivity;
import com.google.firebase.auth.FirebaseAuth;

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
    private ImageView imagePerson;
    private ImageView uploadInformation;
    private TextView txt_name;
    private LinearLayout layout_add_credit;
    private LinearLayout layout_credit;

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
        if(Constant.user.getIsAdmin() == false){
            layout_my_product.setVisibility(View.GONE);
        } else {
            layout_my_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(product , MyProductionList.class);
                    startActivity(intent);
                }
            });
        }

        Glide.with(product).load(Constant.user.getImagePerson()).error(R.drawable.ic_baseline_child_care_24).into(imagePerson);
        txt_name.setText(Constant.user.getName());
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(product, MainActivityLogin.class));
        product.finish();
    }
}