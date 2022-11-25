package com.singh.myshop.Helpers;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singh.myshop.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HelperClass {

    public static String fractionDigits(Double values) {
        DecimalFormat decimalFormat = null;
        decimalFormat = new DecimalFormat("##,##,##0");
        decimalFormat.setMinimumFractionDigits(2);
        return decimalFormat.format(values);
    }

    // Message Layout
    static public TextView ShowPopup(final Dialog myDialog, String title, String message, String type) {
        myDialog.setContentView(R.layout.custom_message_layout);
        myDialog.setCancelable(false);
        LinearLayout msgMainLay=(LinearLayout)myDialog.findViewById(R.id.msgMainLay);
        TextView cancelBtn =(TextView) myDialog.findViewById(R.id.msg_cancle_btn);
        TextView okBtn =(TextView) myDialog.findViewById(R.id.msg_ok_btn);
        TextView msgTitle =(TextView) myDialog.findViewById(R.id.msgTitle);
        TextView msgText =(TextView) myDialog.findViewById(R.id.msgText);
        TextView returnBtn;

        if (type.equals("e")){
            // Message If Error
            returnBtn=cancelBtn;
            okBtn.setVisibility(View.GONE);
            msgTitle.setTextColor(Color.parseColor("#FAFAFA"));
            msgText.setTextColor(Color.parseColor("#FAFAFA"));
            msgMainLay.setBackgroundColor(Color.parseColor("#424242"));
            cancelBtn.setText("OK");
        }else if (type.equals("s")){
            // Message If Success
            returnBtn=cancelBtn;
            okBtn.setVisibility(View.GONE);
            msgTitle.setTextColor(Color.parseColor("#424242"));
            msgText.setTextColor(Color.parseColor("#424242"));
            msgMainLay.setBackgroundColor(Color.parseColor("#FAFAFA"));
            cancelBtn.setText("OK");
        }else{
            // Message If Confirmation
            returnBtn=okBtn;
            okBtn.setVisibility(View.VISIBLE);
            msgTitle.setTextColor(Color.parseColor("#FAFAFA"));
            msgText.setTextColor(Color.parseColor("#FAFAFA"));
            msgMainLay.setBackgroundColor(Color.parseColor("#424242"));
            cancelBtn.setText("IGNORE");
        }
        msgTitle.setText(title);
        msgText.setText(message);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                return;
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        return returnBtn;
    }


    /*............... primary key for ...............*/
    public static String dateTimeForPKey() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /*............... transactionDate Formate yyyy-MM-dd...............*/
    public static String yyyy_mm_dd_currentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    /*............... transactionDate ...............*/
    public static String dd_MM_yyyy_currentDate1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String dd_MM_yyyy_To_yyyy_MM_dd_formatDate(String date) {
        String parsedDate = null;
        try {
            Date initDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = formatter.format(initDate);
        } catch (ParseException e) {e.printStackTrace();}
        return parsedDate;
    }

}
