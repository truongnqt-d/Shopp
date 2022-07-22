package com.example.shoppingapp.sub_fragment_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.ProductDetails;
import com.example.shoppingapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<Production> listProductions;
    private List<Production> listProductionsOld;
    private Context mContext;

        @SuppressLint("NotifyDataSetChanged")
    public ProductionsAdapter(Context context, List<Production> listProductions) {
        this.mContext = context;
        this.listProductions = listProductions;
        this.listProductionsOld = listProductions;
        notifyDataSetChanged();
    }

        @SuppressLint("NotifyDataSetChanged")
    public void addData(List<Production> productions) {
        this.listProductions = productions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_product, parent, false);
        return new ProductionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(listProductions == null) {
            return;
        }
        Production production = listProductions.get(position);
        if(production == null) {
            return;
        }
        ((ProductionsViewHolder) holder).setProductDetails(listProductions.get(position));
    }

    public void release(){
        mContext = null;
    }

    @Override
    public int getItemCount() {
        if(listProductions != null){
            return listProductions.size();
        }
        return 0;
    }

    public class ProductionsViewHolder extends RecyclerView.ViewHolder{
        private final ConstraintLayout layout_item;
        private final ImageView img;
        private final TextView tvTitle, tvPrice;
        private final RatingBar ratingBar;

        public ProductionsViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            img = itemView.findViewById(R.id.images);
            tvTitle = itemView.findViewById(R.id.title);
            tvPrice = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void setProductDetails(Production production) {
            Glide.with(mContext).load(production.getImgProduct()).into(img);
            tvTitle.setText(production.getTitle());
            tvPrice.setText(production.getPrice());
            String rating = production.getRating() == null ? "3.5" : production.getRating().trim();
            ratingBar.setRating(Float.parseFloat(rating));

            layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGoToDetails(production);
                }
            });
        }
        private void onClickGoToDetails(Production production) {
            Intent intent = new Intent(mContext, ProductDetails.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_product", production);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()) {
                    listProductions = null;
                } else {
                    List<Production> productions = new ArrayList<>();
                    for(Production production : listProductionsOld) {
                        if(strSearch.toLowerCase().contains(production.getTitle().toLowerCase())) {
                            productions.add(production);
                        }
                    }

                    listProductions = productions;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listProductions;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listProductions = (List<Production>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
