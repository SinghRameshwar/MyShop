package com.singh.myshop.OrderListG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.singh.myshop.AdapterG.ItemListAdapter;
import com.singh.myshop.AdapterG.OderListAdapter;
import com.singh.myshop.R;

import java.util.HashMap;

public class OrderListView extends Fragment {

    ListView listView;
    HashMap orderMasterList;
    View progressBar1;
    String storeMobNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.order_list_view,container,false);
        final SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences("loginCredentials", Context.MODE_PRIVATE);
        storeMobNo = sharedPref.getString("mob_num", "");

        listView = rootView.findViewById(R.id.listView);
        progressBar1 = getActivity().findViewById(R.id.progressBar1);
        /* Get Data To FCM Realtime Data Base */
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        retriveData();
    }

    void retriveData(){
        progressBar1.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("itemOrder/"+storeMobNo);
        //Query query = ref.orderByChild("date").startAt(formatDate(fromDt)).endAt(formatDate(toDt));
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        orderMasterList = new HashMap();
                        progressBar1.setVisibility(View.GONE);
                        try {
                            orderMasterList = (HashMap) dataSnapshot.getValue();
                            OderListAdapter item_listAdapter = new OderListAdapter(getActivity(), orderMasterList);
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
