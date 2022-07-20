package com.example.shoppingapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductCart extends Fragment {

    private View view;
    private ImageView imgProduct;
    private ImageView imgClear;
    private TextView txtPrice;
    private TextView txtTitle;
    private TextView txtReduce;
    private TextView txtMore;
    private TextView txtQuantity;
    private Button btnAdd;

    private int quantity = 1;
    private FirebaseFirestore firebaseFirestore;
    private Production productAddCart;
    private Production product;
    private String idUser;
    private String idDocumentProduct;
    private static final String TAG = "AddProductCart";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "productions";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddProductCart.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductCart newInstance(Production production) {
        AddProductCart fragment = new AddProductCart();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, production);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_product_cart, container, false);
        // Inflate the layout for this fragment
        initUi();
        initListener();
        return view;
    }

    private void initUi() {
        imgProduct = view.findViewById(R.id.imgProduct);
        imgClear = view.findViewById(R.id.imgClear);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtReduce = view.findViewById(R.id.txtReduce);
        txtQuantity = view.findViewById(R.id.txtQuantity);
        txtMore = view.findViewById(R.id.txtMore);
        btnAdd = view.findViewById(R.id.btnAdd);

        firebaseFirestore = FirebaseFirestore.getInstance();
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void initListener() {
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        product = (Production) getArguments().getSerializable("productions");
        Glide.with(view).load(product.getImgProduct()).into(imgProduct);
        txtTitle.setText(product.getTitle());
        txtPrice.setText(product.getPrice());
        txtQuantity.setText("1");

        txtReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1){
                    --quantity;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity < 20){
                    ++quantity;
                    txtQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataProductCart();
            }
        });
    }

    private void setDataProductCart() {
        firebaseFirestore.collection("cart").whereEqualTo("id", product.getId())
                .whereEqualTo("idUser", idUser)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                                idDocumentProduct = documentSnapshot.getId();
                                updateQuantityProductCart(quantity);
                                Log.d(TAG, "getting documents: ");
                            }
                            Log.d(TAG, "onSuccess: ");
                        } else {
                            addProductCart(quantity);
                        }
                    }
                });
    }

    private void addProductCart(int quantity) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        productAddCart = new Production(product.getId(), idUser, date, quantity, product.getTitle(),
                product.getPrice(), product.getImgProduct(), product.getRating());

        firebaseFirestore.collection("cart").add(productAddCart)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
    }

    private void updateQuantityProductCart(int quantity) {
        firebaseFirestore.collection("cart").document(idDocumentProduct)
                .update("quantity", quantity)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}