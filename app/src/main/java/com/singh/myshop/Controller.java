package com.singh.myshop;

import android.app.Application;

import com.singh.myshop.Helpers.DBHelpers;

public class Controller extends Application {

    private DBHelpers mDbHelper = null;

    public DBHelpers getDbHelper(){
        if(mDbHelper==null){
            mDbHelper=new DBHelpers(this);
        }
        return mDbHelper;
    }

}
