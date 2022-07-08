package com.example.shoppingapp.credit;

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
import com.example.shoppingapp.dataFirebase.Credit;
import com.example.shoppingapp.dataFirebase.ProductAddCart;
import com.example.shoppingapp.sub_recycleView.ProductAdapter;

import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditVewHolDer> {
    private List<Credit> mListCredit;
    private Context mContext;

    public CreditAdapter(List<Credit> list, Context context) {
        this.mListCredit = list;
        this.mContext = context;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<Credit> credit) {
        this.mListCredit = credit;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreditVewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_credit, parent, false);
        return new CreditAdapter.CreditVewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditVewHolDer holder, int position) {
        Credit credit = mListCredit.get(position);
        if(credit == null){
            return;
        }

        holder.cardNumber.setText("Số thẻ: " + credit.getCardNumber());
        holder.expirationDate.setText("Ngày hết hạn: " + credit.getExpirationDate());
        holder.cardHolder.setText("Chủ thẻ: " + credit.getCardHolder());
    }

    @Override
    public int getItemCount() {
        if(mListCredit != null){
            return mListCredit.size();
        }
        return 0;
    }

    public void release(){
        mContext = null;
    }

    public class CreditVewHolDer extends RecyclerView.ViewHolder {
        private TextView cardNumber;
        private TextView expirationDate;
        private TextView cardHolder;

        public CreditVewHolDer(@NonNull View itemView) {
            super(itemView);
            cardNumber = itemView.findViewById(R.id.cardNumber);
            expirationDate = itemView.findViewById(R.id.expirationDate);
            cardHolder = itemView.findViewById(R.id.cardHolder);

        }
    }
}
