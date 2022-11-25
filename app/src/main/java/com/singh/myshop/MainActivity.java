package com.singh.myshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.singh.myshop.DashboardViewG.DashBoardView;
import com.singh.myshop.ItemsListG.ItemsListView;
import com.singh.myshop.Helpers.HelperClass;
import com.singh.myshop.Helpers.NotificationHelpers;
import com.singh.myshop.LoginModelG.LoginModel;
import com.singh.myshop.OrderListG.OrderListView;

import org.json.JSONArray;



public class MainActivity extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    String refreshedToken = "cHGnp0VDQYekytNNPrJFnR:APA91bG4bEiYhT1y1POJO8PjYvF_vFypQL6IWwK1EHBkFWp4oMa9oBhTXU29DnANMsrxfK-3_SgGW7JJaxlcjpZpx6sBmzIP6Vg5ZUD1mHVDyeJbXLosdg7LqrVDXUD00-ihYhXbpPvd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new OrderListView();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.viewpager, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        tabViewRender();

        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("loginCredentials", Context.MODE_PRIVATE);
        final String storeMobNo = sharedPref.getString("mob_num", "");
        TextView textview = findViewById(R.id.text);
        textview.setText("+91 "+storeMobNo);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(refreshedToken);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationHelpers.sendMessage(jsonArray,"Hello","I am MyShop & U?","Http:\\google.com","My Name is Vishal",MainActivity.this);

            }
        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.e(TAG, "Value is: " + value);
//                //scheduleNotification(getNotification( value ) , 0 ) ;
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read value.", error.toException());
//            }
//        });

    }


    void tabViewRender(){
        simpleFrameLayout = (FrameLayout) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        TabLayout.Tab firstTab = tabLayout.newTab();

        firstTab.setText("Order");
        firstTab.setIcon(R.drawable.ic_logo);
        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("ItemList");
        secondTab.setIcon(R.drawable.ic_logo);
        tabLayout.addTab(secondTab);

        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Dashboard");
        thirdTab.setIcon(R.drawable.ic_logo);
        tabLayout.addTab(thirdTab);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new OrderListView();
                        break;
                    case 1:
                        fragment = new ItemsListView();
                        break;
                    case 2:
                        fragment = new DashBoardView();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.viewpager, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}