package com.example.shoppingapp.sub_recycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.dataFirebase.ProductAddCart;
import com.example.shoppingapp.sub_fragment_adapter.Production;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVewHolDer> {
    private List<ProductAddCart> mListProductions;
    private Context mContext;

    public ProductAdapter(List<ProductAddCart> list, Context context) {
        this.mListProductions = list;
        this.mContext = context;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<ProductAddCart> productions) {
        this.mListProductions = productions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductVewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_cart_product, parent, false);
        return new ProductVewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVewHolDer holder, int position) {
        ProductAddCart product = mListProductions.get(position);
        if(product == null){
            return;
        }
        Glide.with(mContext).load(product.getImgProduct()).into(holder.imageView);
        holder.textView_title.setText(product.getTitle());
        holder.textView_price.setText(product.getPrice());
        holder.quantity.setText(String.valueOf(product.getQuantity()));
        holder.description.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        if(mListProductions != null){
            return mListProductions.size();
        }
        return 0;
    }

    public void release(){
        mContext = null;
    }

    public class ProductVewHolDer extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView quantity;
        private TextView description;
        private TextView textView_title;
        private TextView textView_price;

        public ProductVewHolDer(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.images);
            quantity = itemView.findViewById(R.id.quantity);
            description = itemView.findViewById(R.id.description);
            textView_title = itemView.findViewById(R.id.title);
            textView_price = itemView.findViewById(R.id.price);
        }
    }
}
