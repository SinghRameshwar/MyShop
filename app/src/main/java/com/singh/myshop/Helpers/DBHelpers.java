package com.singh.myshop.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelpers extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "MyAccount.db";
    private static final String[] SQL_DELETE_ENTRIES =
            {
                    "DROP TABLE IF EXISTS c_listTable",
                    "DROP TABLE IF EXISTS c_itemListTable",
                    "DROP TABLE IF EXISTS dealer_listTable",
                    "DROP TABLE IF EXISTS dealers_itemsTb",

            };
    private static final String[] SQL_CREATE_ENTRIES =
            {
                    "CREATE TABLE c_listTable(_id VARCHAR PRIMARY KEY, c_name TEXT, c_mob INTEGER DEFAULT 0, c_address TEXT, date TEXT, text1 TEXT, text2 TEXT, text3 TEXT, text4 TEXT)",
                    "CREATE TABLE c_itemListTable(_id VARCHAR PRIMARY KEY, c_id VARCHAR, item TEXT, itemAmt INTEGER, item_qty INTEGER, date TEXT, paidAmt INTEGER DEFAULT 0, text1 TEXT, text2 TEXT, text3 TEXT, text4 TEXT)",
                    "CREATE TABLE dealer_listTable(_id VARCHAR PRIMARY KEY, d_name TEXT, d_mob INTEGER DEFAULT 0, d_address TEXT, date TEXT, text1 TEXT, text2 TEXT, text3 TEXT, text4 TEXT)",
                    "CREATE TABLE dealers_itemsTb(_id VARCHAR PRIMARY KEY, d_id VARCHAR, item TEXT, itemAmt INTEGER, item_qty INTEGER, date TEXT, paidAmt INTEGER DEFAULT 0, text1 TEXT, text2 TEXT, text3 TEXT, text4 TEXT)",
                    "CREATE TABLE fix_itemsale(_id VARCHAR PRIMARY KEY, d_name TEXT, d_mob INTEGER DEFAULT 0, d_address TEXT, date TEXT, text1 TEXT, text2 TEXT, text3 TEXT, text4 TEXT)",
                    "CREATE TABLE fix_itemssaledtls(_id VARCHAR PRIMARY KEY, d_id VARCHAR, item TEXT, itemAmt INTEGER, item_qty INTEGER, date TEXT, paidAmt INTEGER DEFAULT 0, text1 TEXT, text2 TEXT, text3 TEXT, text4 TEXT)",

            };

    public DBHelpers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        for(String createQry:SQL_CREATE_ENTRIES)
            db.execSQL(createQry);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                //Previous upgrade policy was to simply to discard the data and start over
                for(String deleteQry:SQL_DELETE_ENTRIES)
                    db.execSQL(deleteQry);
                onCreate(db);
                break;
        }
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}