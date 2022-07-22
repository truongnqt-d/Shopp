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
import com.example.shoppingapp.dataFirebase.Advert;
import com.example.shoppingapp.sub_fragment_adapter.Production;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {
    private List<Advert> adverts;
    private Context context;

    @SuppressLint("NotifyDataSetChanged")
    public AdvertAdapter(Context context, List<Advert> adverts) {
        this.adverts = adverts;
        this.context = context;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<Advert> adverts) {
        this.adverts = adverts;
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
        ((AdvertViewHolder) holder).setProductDetails(adverts.get(position));
    }


    private void setValue(AdvertViewHolder holder, Production production) {
        Glide.with(context).load(production.getImgProduct()).into(holder.img);
        String rating = production.getRating() == null ? "3" : production.getRating().trim();
    }

    @Override
    public int getItemCount() {
        if(adverts != null) {
            return adverts.size();
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

        public void setProductDetails(Advert advert) {
            Glide.with(context).load(advert.getImgProduct()).into(img);
            title.setText(advert.getTitle());

//            layoutItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickGoToDetails(advert);
//                }
//            });
        }
//        private void onClickGoToDetails(Advert advert) {
//            Intent intent = new Intent(context, ProductDetails.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("object_product", advert);
////            bundle.putParcelable("object_product", production);
//            intent.putExtras(bundle);
//            context.startActivity(intent);
//        }

    }
}
