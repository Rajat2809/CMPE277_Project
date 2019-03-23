package com.application.project.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HEALTHYFOODMANAGER";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE HEALTHYTABLE (NAME VARCHAR(100),CALORIES INT, NUTRIENTS VARCHAR(250), DIETITIAN VARCHAR(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert (SQLiteDatabase db, String name, String calories, String nutrients, String dietitian)
    {
        db.execSQL("DROP TABLE HEALTHYTABLE");
        db.execSQL("CREATE TABLE HEALTHYTABLE (NAME VARCHAR(100),CALORIES INT, NUTRIENTS VARCHAR(250), DIETITIAN VARCHAR(100))");

        //  db.execSQL("DROP TABLE HEALTHYTABLE");
        // db.execSQL("CREATE TABLE HEALTHYTABLE (NAME VARCHAR(100),CALORIES INT, NUTRIENTS VARCHAR(250), DIETITIAN VARCHAR(100))");

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Calories",calories);
        contentValues.put("Nutrients",nutrients);
        contentValues.put("Dietitian",dietitian);

        db.insert("HEALTHYTABLE",null ,contentValues);

        Cursor res = db.rawQuery("select * from HEALTHYTABLE",null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Name :"+ res.getString(0)+"\n");
            buffer.append("Calories :"+ res.getString(1)+"\n");
            buffer.append("Nutrients :"+ res.getString(2)+"\n");
            buffer.append("Dietitian :"+ res.getString(3)+"\n");
        }

    }

    public void update (SQLiteDatabase db, String item, String details)
    {
       /* Log.d("Test", item);
        ContentValues contentValues = new ContentValues();
        contentValues.put("Details",details);

        db.update("TODO",contentValues ,"ITEM = ?",new String[] { item });


        Cursor res = db.rawQuery("select * from TODO",null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Item :"+ res.getString(0)+"\n");
            buffer.append("Details :"+ res.getString(1)+"\n");

        }

*/
    }

    public void delete (SQLiteDatabase db, String item)
    {

        // db.delete("TODO" ,"ITEM = ?",new String[] { item });

    }
    public Cursor retrieve (SQLiteDatabase db, String dietitian)
    {
        Cursor res = db.rawQuery("select * from HEALTHYTABLE ",null);


        return res;
    }

    public Cursor retrieveDetails (SQLiteDatabase db, String name, String dietitian)
    {
        Cursor res = db.rawQuery("select CALORIES,NUTRIENTS from HEALTHYTABLE WHERE NAME = ? AND DIETITIAN = ?",new String[] { name,dietitian });

        return res;
    }
}