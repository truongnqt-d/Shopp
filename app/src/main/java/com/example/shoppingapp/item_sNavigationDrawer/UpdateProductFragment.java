package com.example.shoppingapp.item_sNavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.MyProductionList;
import com.example.shoppingapp.R;
import com.example.shoppingapp.UpdateProductActivity;
import com.example.shoppingapp.dataFirebase.Product;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProductFragment extends Fragment {
    private static final String TAG = "UpdateProductFragment";
    private static int TYPE_VIEW_PRODUCT = 1;
    private static int TYPE_VIEW_PRODUCT_SALE = 2;

    private FirestoreRecyclerAdapter adapter;

    private RecyclerView rcvData;
    private MyProductionList myProductionList;


    public UpdateProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi();
        initListener();
    }

    private void initUi() {
        myProductionList = (MyProductionList) getActivity();
        rcvData = myProductionList.findViewById(R.id.rcv_data);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String idEmail = user.getUid();

//        query = FirebaseFirestore.getInstance().document("users/" + idEmail)
//                .collection("product");
        Query query = FirebaseFirestore.getInstance().document("users/" + idEmail)
                .collection("productSale");


        FirestoreRecyclerOptions<Product> getProduct = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class).build();

        adapter = new FirestoreRecyclerAdapter<Product, ProductionsViewHolder>(getProduct) {

            @NonNull
            @Override
            public ProductionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_rcv_product, parent, false);
                return new ProductionsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductionsViewHolder holder, int position, @NonNull Product model) {
                ((ProductionsViewHolder) holder).bind(model);
                Log.d(TAG, "onBindViewHolder: " + position);
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Log.d(TAG, e.getLocalizedMessage());
            }
        };
        rcvData.setHasFixedSize(true);
        rcvData.setLayoutManager(new LinearLayoutManager(myProductionList));
        rcvData.setAdapter(adapter);

    }

    private void initListener() {
    }

    public class ProductionsViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout layout_item;
        private final ImageView img;
        private final TextView tvTitle, tvPrice, tvDescription;
        private final RatingBar ratingBar;

        public ProductionsViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            img = itemView.findViewById(R.id.images);
            tvTitle = itemView.findViewById(R.id.title);
            tvPrice = itemView.findViewById(R.id.price);
            tvDescription = itemView.findViewById((R.id.description));
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bind (Product production) {
            Glide.with(getContext()).load(production.getImgProduct()).into(img);
            tvTitle.setText(production.getTitle());
            tvPrice.setText(production.getPrice());
            tvDescription.setText(production.getDescription());
            String rating = production.getRating() == null ? "3.5" : production.getRating().trim();
            ratingBar.setRating(Float.parseFloat(rating));

            layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGoToDetails(production);
                }
            });
        }
        private void onClickGoToDetails(Product production) {
            Intent intent = new Intent(getContext(), UpdateProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_product", production);
//            bundle.putParcelable("object_product", production);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        }
    }

    public class ProductSaleViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layoutItem;
        private ImageView img;
        private TextView title;
        private TextView percent;
        private TextView description;
        private TextView price;
        private RatingBar ratingBar;

        public ProductSaleViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutItem = itemView.findViewById(R.id.layout_item);
            img = itemView.findViewById(R.id.images);
            title = itemView.findViewById(R.id.title);
            percent = itemView.findViewById(R.id.percent);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bind (Production production) {
            Glide.with(getContext()).load(production.getImgProduct()).into(img);
            title.setText(production.getTitle());
            percent.setText(production.getPercent());
            description.setText(production.getDescription());
            price.setText(production.getPrice());
            String rating = production.getRating() == null ? "3.5" : production.getRating().trim();
            ratingBar.setRating(Float.parseFloat(rating));

            layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGoToDetails(production);
                }
            });
        }
        private void onClickGoToDetails(Production production) {
            Intent intent = new Intent(getContext(), UpdateProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_product", production);
//            bundle.putParcelable("object_product", production);
            intent.putExtras(bundle);
            getContext().startActivity(intent);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
