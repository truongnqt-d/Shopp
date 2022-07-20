package com.example.shoppingapp.sub_recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;

import java.util.List;
//import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter <NotificationAdapter.notificationViewHoDel>{

    private List <Production> listProductions;

    public NotificationAdapter(List<Production> listProductions) {
        this.listProductions = listProductions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notificationViewHoDel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_notification, parent, false);
        return new notificationViewHoDel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationViewHoDel holder, int position) {
        Production production = listProductions.get(position);
        if(production == null) {
            return;
        }

        holder.txtTitle.setText(production.getTitle());
        holder.txtDescription1.setText(production.getPrice());
    }

    @Override
    public int getItemCount() {
        if(listProductions != null) {
            return listProductions.size();
        }
        return 0;
    }

    public class notificationViewHoDel extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDescription1;
        TextView txtDescription2;

        public notificationViewHoDel(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.title);
            txtDescription1 = itemView.findViewById(R.id.description1);
            txtDescription2 = itemView.findViewById(R.id.description2);
        }
    }
}
