package com.example.shoppingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shoppingapp.CartProductActivity;
import com.example.shoppingapp.ProductActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.dataFirebase.Advert;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.example.shoppingapp.sub_fragment_adapter.ProductionsAdapter;
import com.example.shoppingapp.view_pager.AdvertAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    private static final String TAG = "ProductFragment";
    private RecyclerView rcvData;
    private ProductActivity productActivity;
    private ProductionsAdapter productionsAdapter;
    private AdvertAdapter advertActivity;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Advert> advertArrayList = new ArrayList<>();
    private List<Production> productionArrayList = new ArrayList<>();
    private ImageView imgCart;
    private ImageView imgSearchView;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem() == advertArrayList.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
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
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        initListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }

    private void initUi(View view) {
        productActivity = (ProductActivity) getActivity();

        viewPager2 = view.findViewById(R.id.view_pager);
        circleIndicator3 = view.findViewById(R.id.circle_indicator);
        imgCart = view.findViewById(R.id.cart);
        imgSearchView = view.findViewById(R.id.imgSearchView);

        rcvData = view.findViewById(R.id.rcv_data);
    }

    private void initListener() {
        setListProductFirebase();
        setListProductViewPager2();

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickCart();
            }
        });

        imgSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickSearch();
            }
        });
    }

    private void setListProductViewPager2() {
        setUpMoviesViewPager2();

        advertArrayList = getListAdvertFirebase();
        advertActivity = new AdvertAdapter(productActivity, getListAdvertFirebase());
        viewPager2.setAdapter(advertActivity);
        circleIndicator3.setViewPager(viewPager2);

//        viewPager2.setPageTransformer(new DepthPageTransformer());
    }

    private void setUpMoviesViewPager2() {
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(1);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });

        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

    }

    private List<Advert> getListAdvertFirebase() {
        List<Advert> listAdvert = new ArrayList<>();

//        progressDialog.show();
        FirebaseFirestore getAdvert = FirebaseFirestore.getInstance();
        getAdvert.collection("advert")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot queryDocumentSnapshot1 : task.getResult()){
                        listAdvert.add(queryDocumentSnapshot1.toObject(Advert.class));
                    }
                if (listAdvert.size() > 0) {
                    advertActivity.addData(listAdvert);
                    circleIndicator3.getAdapterDataObserver().onChanged();
                    advertArrayList = listAdvert;
                    Log.d(TAG, "isSuccessful");
                }
                }
            }
        });
        return listAdvert;
    }

    private void setListProductFirebase() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvData.setLayoutManager(staggeredGridLayoutManager);

        productionsAdapter = new ProductionsAdapter(productActivity, getListProduct());
        rcvData.setAdapter(productionsAdapter);
    }

    private List<Production> getListProduct() {
        List<Production> listProduct = new ArrayList<>();

        FirebaseFirestore getProductSales = FirebaseFirestore.getInstance();
        getProductSales.collection("products")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            Production production = documentSnapshot.toObject(Production.class);
                            production.setDocument(documentSnapshot.getId());
                            listProduct.add(production);
                        }
                        if (listProduct.size() > 0) {
                            productionsAdapter.addData(listProduct);
                            productionArrayList = listProduct;
                            Log.d(TAG, "isSuccessful");
                        }
                    }
                }
            });
        return listProduct;
    }

    private void onclickCart() {
        Bundle bundle = new Bundle();
        startActivity(new Intent(productActivity, CartProductActivity.class));
    }

    private void onclickSearch() {
        DialogFragment dialogFragment = new SearchItemProduct().newInstance(productionArrayList);
        dialogFragment.show(productActivity.getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(productionsAdapter != null){
            productionsAdapter.release();
        }

        if(advertActivity != null) {
            advertActivity.release();
        }
    }

//    private List<Bitmap> getListAssets() {
//        List<Bitmap> bitmapsList = new ArrayList<>();
//        try {
//            AssetManager manager = productActivity.getAssets();
//            String prefix = "img_test (";
////            String[] files = manager.list("");
//            Bitmap bitmap;
//            String pName;
//            for (int i = 1; i < 10; i++) {
//                pName = prefix + i + ").jpg";
//                bitmap = BitmapFactory.decodeStream(manager.open(pName));
//                bitmapsList.add(bitmap);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.d("TAG", "addAssetsImages: " + bitmapsList.size());
//        return bitmapsList;
//    }

}