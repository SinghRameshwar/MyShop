package com.singh.myshop.ItemsListG;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.singh.myshop.AdapterG.ItemListAdapter;
import com.singh.myshop.OrderListG.OrderListView;
import com.singh.myshop.R;
import com.singh.myshop.StockEntryG.StockEntryView;

import java.util.HashMap;

public class ItemsListView extends Fragment {

    ListView listView;
    HashMap itemMasterList;
    View progressBar1;
    CardView addItemBtn;
    Dialog myDialog;
    String storeMobNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.food_list_view,container,false);
        myDialog = new Dialog(getContext());
        final SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences("loginCredentials", Context.MODE_PRIVATE);
        storeMobNo = sharedPref.getString("mob_num", "");
        listView = rootView.findViewById(R.id.listView);
        progressBar1 = rootView.findViewById(R.id.progressBar1);
        addItemBtn = rootView.findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockEntryView.entryViewLayout(myDialog,getContext());
            }
        });
        /* Get Data To FCM Realtime Data Base */
        retriveData();
        return rootView;
    }

    public void retriveData(){
        if (progressBar1 != null)
        progressBar1.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("itemStock/"+storeMobNo);
        //Query query = ref.orderByChild("date").startAt(formatDate(fromDt)).endAt(formatDate(toDt));
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        itemMasterList = new HashMap();
                        if (progressBar1 != null)
                            progressBar1.setVisibility(View.GONE);
                        try {
                            itemMasterList = (HashMap) dataSnapshot.getValue();
                            ItemListAdapter item_listAdapter = new ItemListAdapter(getActivity(), itemMasterList);
                            listView.setAdapter(item_listAdapter);
                            item_listAdapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }
}
