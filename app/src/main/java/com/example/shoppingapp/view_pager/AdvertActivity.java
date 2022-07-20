package com.example.shoppingapp.view_pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.ProductDetails;
import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;

import java.util.List;

public class AdvertActivity extends RecyclerView.Adapter<AdvertActivity.AdvertViewHolder> {
    private List<Production> listProduct;
    private Context context;

    @SuppressLint("NotifyDataSetChanged")
    public AdvertActivity(Context context, List<Production> productions) {
        this.listProduct = productions;
        this.context = context;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<Production> productions) {
        this.listProduct = productions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vpg_product_sale, parent, false);

        return new AdvertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        ((AdvertViewHolder) holder).setProductDetails(listProduct.get(position));
    }


    private void setValue(AdvertViewHolder holder, Production production) {
        Glide.with(context).load(production.getImgProduct()).into(holder.img);
        String rating = production.getRating() == null ? "3" : production.getRating().trim();
    }

    @Override
    public int getItemCount() {
        if(listProduct != null) {
            return listProduct.size();
        }
        return 0;
    }

    public void release(){
        context = null;
    }

    public class AdvertViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layoutItem;
        private ImageView img;
        private TextView title;

        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutItem = itemView.findViewById(R.id.layout_item);
            img = itemView.findViewById(R.id.images);
            title = itemView.findViewById(R.id.title);
        }

        public void setProductDetails(Production production) {
            Glide.with(context).load(production.getImgProduct()).into(img);
            title.setText(production.getTitle());

            layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGoToDetails(production);
                }
            });
        }
        private void onClickGoToDetails(Production production) {
            Intent intent = new Intent(context, ProductDetails.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object_product", production);
//            bundle.putParcelable("object_product", production);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

    }
}
