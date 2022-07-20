package com.example.shoppingapp.item_sNavigationDrawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.MyProductionList;
import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DeleteProductFragment extends Fragment {
    private RecyclerView rcvData;
    private String idEmail;
    private MyProductionList myProductionList;

    public DeleteProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
        initListener();
    }

    private void initUi() {
        rcvData = getView().findViewById(R.id.rcv_data);
        myProductionList = (MyProductionList) getActivity();
    }

    private void initListener() {
        getUserInformationFirebase();
    }

    private void getUserInformationFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idEmail = user.getUid();

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            rcvData.setLayoutManager(gridLayoutManager);

        }
    }

    private List<Production> getListProduct() {
        List<Production> listDataFirebase = new ArrayList<>();

        FirebaseFirestore getData = FirebaseFirestore.getInstance();
        getData.collection("products").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                listDataFirebase.add(documentSnapshot.toObject(Production.class));
                            }
                        }
                        if(listDataFirebase.size() > 0) {
                        }
                    }
                });

        getData.collection("productSales").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(DocumentSnapshot documentSnapshot : task.getResult()) {
                                listDataFirebase.add(documentSnapshot.toObject(Production.class));
                            }
                        }
                        if(listDataFirebase.size() > 0) {
                        }
                    }
                });

        return listDataFirebase;
    }
}
