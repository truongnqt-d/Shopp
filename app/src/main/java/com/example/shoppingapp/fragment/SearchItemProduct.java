package com.example.shoppingapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shoppingapp.CartProductActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.example.shoppingapp.sub_fragment_adapter.ProductionsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchItemProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchItemProduct extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ImageView imgCart;
    private SearchView search;
    private View view;
    private RecyclerView rcvDataProduct;
    private ProductionsAdapter productionsAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchItemProduct() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog);
    }

    public static SearchItemProduct newInstance(List<Production> productions) {
        SearchItemProduct fragment = new SearchItemProduct();
        Bundle args = new Bundle();
        args.putParcelableArrayList("listProduct", (ArrayList<? extends Parcelable>) productions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_item_product, container, false);
        initUi(view);
        initListener();
        return view;
    }

    private void initUi(View view) {
        imgCart = view.findViewById(R.id.imgCart);
        search = view.findViewById(R.id.search);
        search.onActionViewExpanded();
        rcvDataProduct = view.findViewById(R.id.rcvDataProduct);
        productionsAdapter = new ProductionsAdapter(getActivity(), getProductArgument());
    }

    private void initListener() {
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartProductActivity.class));
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productionsAdapter.getFilter().filter(query);
                setAdapterRcv();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productionsAdapter.getFilter().filter(newText);
                setAdapterRcv();
                return false;
            }
        });
    }

    private void setAdapterRcv() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rcvDataProduct.setLayoutManager(staggeredGridLayoutManager);
        rcvDataProduct.setAdapter(productionsAdapter);
    }

    private List<Production> getProductArgument() {
        List<Production> production = getArguments().getParcelableArrayList("listProduct");
        return production;
    }
}