package com.singh.myshop.AdapterG;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.singh.myshop.Helpers.HelperClass;
import com.singh.myshop.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ItemListAdapter extends BaseAdapter implements Filterable {

    public HashMap myList= new HashMap();
    public HashMap myListTemp= new HashMap();
    Context context;

    public ItemListAdapter(Context context, HashMap myList) {
        super();
        this.myList=myList;
        this.myListTemp=myList;
        this.context = context;
    }

    @Override
    public int getCount() {
        try {
            return myListTemp.size();
        }catch (Exception e){}
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return myListTemp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_list_cellview, parent, false);
        }
        TextView itemName=(TextView)convertView.findViewById(R.id.itemName);
        TextView itemPrice=(TextView)convertView.findViewById(R.id.itemPrice);
        TextView itemDescreption=(TextView)convertView.findViewById(R.id.itemDescreption);

        Object firstKey = myListTemp.keySet().toArray()[i];
        HashMap<String,String> valueForFirstKey = (HashMap<String, String>) myListTemp.get(firstKey);
        convertView.setTag(firstKey);

        itemName.setText(valueForFirstKey.get("itemName"));
        itemDescreption.setText(valueForFirstKey.get("itemDesc"));
        try {
            itemPrice.setText("Price per Pic:- ₹ "+HelperClass.fractionDigits(Double.parseDouble(valueForFirstKey.get("itemRate"))));
        }catch (Exception e){}

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                myListTemp= (HashMap) results.values;
                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<HashMap<String,String>> FilteredList= new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = myList;
                    results.count = myList.size();
                }else{
                    for (int i = 0; i < myList.size(); i++) {
                        HashMap<String,String> data = (HashMap<String, String>) myList.get(i);
                        if (data.get("expend_type").toString().toLowerCase().contains(constraint.toString().toLowerCase())||
                                data.get("date").toString().toLowerCase().contains(constraint.toString().toLowerCase())||
                                data.get("expend_qty").toString().toLowerCase().contains(constraint.toString().toLowerCase())){
                            FilteredList.add(data);
                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();
                }
                return results;
            }
        };

        return filter;
    }

    public static String formatDate (String date) {
        String parsedDate = null;
        try {
            Date initDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            parsedDate = formatter.format(initDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

}
