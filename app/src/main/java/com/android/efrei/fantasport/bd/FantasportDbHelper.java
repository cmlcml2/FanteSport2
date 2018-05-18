package com.android.efrei.fantasport.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class FantasportDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fantasport.db";


    FantasportDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("FTS", "Instanciation de FantasportDbHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("FTS", "Création des tables");
        db.execSQL(FantasportContract.Match.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("FTS", "Upgrade des tables");
        db.execSQL(FantasportContract.Match.SQL_DROP_TABLE);
    }
}
