package com.example.shoppingapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.ProductActivity;
import com.example.shoppingapp.R;
import com.example.shoppingapp.sub_fragment_adapter.Production;
import com.example.shoppingapp.sub_recycleView.ChatHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatHistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatHistoryFragment newInstance(String param1, String param2) {
        ChatHistoryFragment fragment = new ChatHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_histor_product, container, false);
        ProductActivity productActivity = (ProductActivity) getActivity();

        LinearLayoutManager layoutManager = new LinearLayoutManager(productActivity);

        RecyclerView rcv = view.findViewById(R.id.recycler_view);
        rcv.setLayoutManager(layoutManager);

        ChatHistoryAdapter chatHistoryAdapter = new ChatHistoryAdapter(getList(), getActivity());
        rcv.setAdapter(chatHistoryAdapter);

        return view;
    }

    private List<Production> getList() {
        List<Production> productionList = new ArrayList<>();
//        productionList.add(new Production(R.drawable.ic_person, "Name", "message", "time"));
//        productionList.add(new Production(R.drawable.ic_person, "Name", "message", "time"));
//        productionList.add(new Production(R.drawable.ic_person, "Name", "message", "time"));
//        productionList.add(new Production(R.drawable.ic_person, "Name", "message", "time"));
//        productionList.add(new Production(R.drawable.ic_person, "Name", "message", "time"));
        return productionList;
    }
}