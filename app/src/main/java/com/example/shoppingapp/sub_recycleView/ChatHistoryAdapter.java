package com.example.shoppingapp.sub_recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;

import java.util.List;

public class ChatHistoryAdapter extends RecyclerView.Adapter <ChatHistoryAdapter.ViewHolDel> {
    private List<Production> listProduct;

    public ChatHistoryAdapter(List<Production> listProduct) {
        this.listProduct = listProduct;
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

        holder.imageView.setImageResource(production.getResourceId());
        holder.txtName.setText(production.getTitle());
        holder.txtTime.setText(production.getPrice());
        holder.txtMessage.setText(production.getDescription());
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
