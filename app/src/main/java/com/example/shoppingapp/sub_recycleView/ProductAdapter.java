package com.example.shoppingapp.sub_recycleView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVewHolDer> {
    private List<Production> mListProductions;
    private Context mContext;
    private static final String TAG = "ProductAdapter";

    public ProductAdapter(List<Production> list, Context context) {
        this.mListProductions = list;
        this.mContext = context;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<Production> productions) {
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
        Production product = mListProductions.get(position);
        if(product == null){
            return;
        }
        Glide.with(mContext).load(product.getImgProduct()).into(holder.imageView);
        holder.textView_title.setText(product.getTitle());
        holder.textView_price.setText(product.getPrice());
        holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
        holder.txtReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.getQuantity() > 1){
                    product.setQuantity(product.getQuantity() - 1);
                    holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
                }
                if(product.getQuantity() == 1){
                    deleteProductCart(product);
                }
            }
        });

        holder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product.getQuantity() < 20){
                    product.setQuantity(product.getQuantity() + 1);
                    holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
                }
            }
        });
    }

    private void deleteProductCart(Production product) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.document("cart/" + product.getDocument()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "delete success: ");
                }
            }
        });
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
        private TextView txtQuantity;
        private TextView textView_title;
        private TextView textView_price;
        private TextView txtReduce;
        private TextView txtMore;

        public ProductVewHolDer(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.images);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            textView_title = itemView.findViewById(R.id.title);
            textView_price = itemView.findViewById(R.id.price);
            txtReduce = itemView.findViewById(R.id.txtReduce);
            txtMore = itemView.findViewById(R.id.txtMore);
        }
    }
}
