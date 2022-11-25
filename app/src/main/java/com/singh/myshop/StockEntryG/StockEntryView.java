package com.singh.myshop.StockEntryG;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.singh.myshop.Helpers.HelperClass;
import com.singh.myshop.ItemsListG.ItemsListView;
import com.singh.myshop.R;

public class StockEntryView{

    private static Dialog rooView;
    private static Context context1;
    private static  EditText itemNameE,itmQtyE,itempriceE,itemDescE;
    private static TextView itmsaveBtn,itmcaclBtn;


    public static void entryViewLayout(Dialog myDialog, Context context){
        rooView = myDialog;
        context1 = context;
        myDialog.setContentView(R.layout.item_entry_view);
        myDialog.setCancelable(false);
        itemNameE = rooView.findViewById(R.id.itemNameE);
        itmQtyE = rooView.findViewById(R.id.itmQtyE);
        itempriceE = rooView.findViewById(R.id.itempriceE);
        itemDescE = rooView.findViewById(R.id.itemDescE);
        itmsaveBtn = rooView.findViewById(R.id.itmsaveBtn);
        itmcaclBtn = rooView.findViewById(R.id.itmcaclBtn);
        itmsaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextBlankCheck(itemNameE).equals("")){
                    Toast.makeText(context, "Please Enter Item Name !", Toast.LENGTH_SHORT).show();
                }else {
                boolean entyrResult = newItemInsert(editTextBlankCheck(itemNameE), editTextBlankCheck(itmQtyE),editTextBlankCheck(itempriceE),editTextBlankCheck(itemDescE));
                if (entyrResult) {
                    myDialog.dismiss();
                    ItemsListView itemsListView = new ItemsListView();
                    itemsListView.retriveData();

                }
                }
            }
        });
        itmcaclBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    static String editTextBlankCheck(EditText editText){
        if (editText.length()<=0){
            return "";
        }else if (editText.getText().equals(null)){
            return "";
        }else if (editText.getText().equals("")){
            return "";
        }
        return editText.getText()+"";
    }

    static boolean newItemInsert(String itemName, String itemQty, String itemrate, String itemDesc){
        try {
            final SharedPreferences sharedPref = context1.getApplicationContext().getSharedPreferences("loginCredentials", Context.MODE_PRIVATE);
            final String storeMobNo = sharedPref.getString("mob_num", "");

            StockEntryModel dataHolder=new StockEntryModel(itemName,itemQty,itemrate,itemDesc,HelperClass.dateTimeForPKey());
            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference user_nood=db.getReference("itemStock/");
            user_nood.child(storeMobNo+"/"+HelperClass.dateTimeForPKey()).setValue(dataHolder);
            return true;
        }catch (Exception e){
            Log.e("Item Insert Error:",e.toString());
            Toast.makeText(context1, "Sorry: "+e, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
