package com.singh.myshop.CrashReport;

import android.app.Activity;
import android.content.Intent;

import com.singh.myshop.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  Created by Arnab Kar on 3/11/15.
 *  This is a custom crash report sender class
 */
public class CrashReportSender {
    private Activity activity;
    public CrashReportSender(Activity activity){
        this.activity=activity;
    }
    public void sendReport(){

        String line,trace="";
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(activity
                            .openFileInput("stack.trace")));
            while((line = reader.readLine()) != null) {
                trace += line+"\n";
            }
        }  catch(IOException ioe) {
            trace="";
        }
        if(!trace.equals("")){
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            String subject = activity.getResources().getString(R.string.ErrorReport);
            String body =
                    "Mail this to adansa.ios@gmail.com: "+
                            "\n"+
                            trace+
                            "\n";

            sendIntent.putExtra(Intent.EXTRA_EMAIL,
                    new String[] {"adansa.ios@gmail.com"});
            sendIntent.putExtra(Intent.EXTRA_TEXT, body);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            sendIntent.setType("message/rfc822");

            activity.startActivity(
                    Intent.createChooser(sendIntent, "Send crash report using:"));

            activity.deleteFile("stack.trace");
        }
        else {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            String subject = activity.getResources().getString(R.string.ErrorReport);
            String body =
                    "Mail this to the Android Developer: "+
                            "\n"+
                            trace+
                            "\n";

            sendIntent.putExtra(Intent.EXTRA_EMAIL,
                    new String[] {"adansa.ios@gmail.com"});
            sendIntent.putExtra(Intent.EXTRA_TEXT, body);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            sendIntent.setType("message/rfc822");

            activity.startActivity(
                    Intent.createChooser(sendIntent, "Send crash report using:"));
        }
    }
}
