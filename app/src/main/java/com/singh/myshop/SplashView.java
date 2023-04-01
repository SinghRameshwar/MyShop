package com.singh.myshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class SplashView extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    Handler handler;
    Runnable runnable;
    String LOG_DBG=this.getClass().getSimpleName();

    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("loginCredentials", Context.MODE_PRIVATE);
        final String storeMobNo = sharedPref.getString("mob_num", "");
        if(storeMobNo.equals("")){
            setContentView(R.layout.activity_splash);
            handler=new Handler();
            runnable=new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashView.this,LoginView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            };
            handler.postDelayed(runnable, SPLASH_TIME_OUT);
        }else {
            Intent intent = new Intent(SplashView.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(handler!=null)
            handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(handler!=null)
            handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }

}