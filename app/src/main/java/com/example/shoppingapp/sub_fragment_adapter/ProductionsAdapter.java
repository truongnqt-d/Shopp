package com.example.shoppingapp.sub_fragment_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.ProductDetails;
import com.example.shoppingapp.R;

import java.util.List;

public class ProductionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Production> listProductions;
    private Context mContext;
    private static int TYPE_VIEW_PRODUCT = 1;
    private static int TYPE_VIEW_ADVERT = 2;

        @SuppressLint("NotifyDataSetChanged")
    public ProductionsAdapter(Context context, List<Production> list) {
        this.mContext = context;
        this.listProductions = list;
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
        if(TYPE_VIEW_PRODUCT == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_product, parent, false);
            return new ProductionsViewHolder(view);
        } else if(TYPE_VIEW_ADVERT == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_product_sale, parent, false);
            return new ProductSaleViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Production production = listProductions.get(position);
        if(production == null) {
            return;
        }
        if(TYPE_VIEW_PRODUCT == holder.getItemViewType()) {
            ((ProductionsViewHolder) holder).setProductDetails(listProductions.get(position));
        } else if(TYPE_VIEW_ADVERT == holder.getItemViewType()) {
            ((ProductSaleViewHolder) holder).setProductDetails(listProductions.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Production production = listProductions.get(position);
        if(production.getItemViewType() == false && production.getPrice() == null) {
            return TYPE_VIEW_ADVERT;
        } else {
            return TYPE_VIEW_PRODUCT;
        }
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
//            bundle.putParcelable("object_product", production);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    public class ProductSaleViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layoutItem;
        private ImageView img;
        private TextView title;
        private TextView price;
        private RatingBar ratingBar;

        public ProductSaleViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutItem = itemView.findViewById(R.id.layout_item);
            img = itemView.findViewById(R.id.images);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void setProductDetails(Production production) {
            Glide.with(mContext).load(production.getImgProduct()).into(img);
            title.setText(production.getTitle());
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
            Intent intent = new Intent(mContext, ProductDetails.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_product", production);
//            bundle.putParcelable("object_product", production);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }

    }
}
