package com.example.shoppingapp.sub_recycleView;

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
import com.example.shoppingapp.sub_fragment_adapter.Production;

import java.util.List;

public class ChatHistoryAdapter extends RecyclerView.Adapter <ChatHistoryAdapter.ViewHolDel> {
    private List<Production> listProduct;
    private Context context;

    public ChatHistoryAdapter(List<Production> listProduct, Context context) {
        this.listProduct = listProduct;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolDel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_chat_history, parent,false);
        return new ViewHolDel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolDel holder, int position) {
        Production production = listProduct.get(position);
        if(production == null) {
            return;
        }

        Glide.with(context).load(production.getImgProduct()).into(holder.imageView);
        holder.txtName.setText(production.getTitle());
        holder.txtTime.setText(production.getPrice());
    }

    @Override
    public int getItemCount() {
        if(listProduct != null) {
            return listProduct.size();
        }
        return 0;
    }

    public class ViewHolDel extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtName;
        private TextView txtMessage;
        private TextView txtTime;

        public ViewHolDel(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.person);
            txtName = itemView.findViewById(R.id.name);
            txtMessage = itemView.findViewById(R.id.message);
            txtTime = itemView.findViewById(R.id.time);
        }
    }
}
