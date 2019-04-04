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

        db.execSQL("CREATE TABLE HEALTHYTABLE (NAME VARCHAR(100),CALORIES INT, CARBOHYDRATES VARCHAR(100), PROTEIN VARCHAR(100),FAT VARCHAR(100), DIETITIAN VARCHAR(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert (SQLiteDatabase db, String name, String calories, String carbohydrates, String protein, String fat, String dietitian)
    {
       // db.execSQL("DROP TABLE HEALTHYTABLE");
       // db.execSQL("CREATE TABLE HEALTHYTABLE (NAME VARCHAR(100),CALORIES INT, CARBOHYDRATES VARCHAR(100), PROTEIN VARCHAR(100),FAT VARCHAR(100), DIETITIAN VARCHAR(100))");

        //  db.execSQL("DROP TABLE HEALTHYTABLE");
        // db.execSQL("CREATE TABLE HEALTHYTABLE (NAME VARCHAR(100),CALORIES INT, NUTRIENTS VARCHAR(250), DIETITIAN VARCHAR(100))");

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Calories",calories);
        contentValues.put("Carbohydrates",carbohydrates);
        contentValues.put("Protein",protein);
        contentValues.put("Fat",fat);
        contentValues.put("Dietitian",dietitian);


        db.insert("HEALTHYTABLE",null ,contentValues);

        Cursor res = db.rawQuery("select * from HEALTHYTABLE",null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Name :"+ res.getString(0)+"\n");
            buffer.append("Calories :"+ res.getString(1)+"\n");
            buffer.append("Carbohydrates :"+ res.getString(2)+"\n");
            buffer.append("Protein :"+ res.getString(3)+"\n");
            buffer.append("Fat :"+ res.getString(4)+"\n");
            buffer.append("Dietitian :"+ res.getString(5)+"\n");
        }

    }

    public void update (SQLiteDatabase db, String currentname, String name, String calories, String carbohydrates, String protein, String fat)
    {

        ContentValues contentValues = new ContentValues();

        if(!name.equals("")) {
            contentValues.put("Name", name);
        }
        if(!calories.equals(""))
            contentValues.put("Calories",calories);
        if(!carbohydrates.equals(""))
            contentValues.put("Carbohydrates",carbohydrates);
        if(!protein.equals(""))
            contentValues.put("Protein",protein);
        if(!fat.equals(""))
            contentValues.put("Fat",fat);

        db.update("HEALTHYTABLE",contentValues ,"NAME = ?",new String[] { currentname });



    }

    public void delete (SQLiteDatabase db, String name)
    {

        db.delete("HEALTHYTABLE" ,"NAME = ?",new String[] { name });

    }
    public Cursor retrieve (SQLiteDatabase db, String dietitian)
    {
        Cursor res = db.rawQuery("select NAME from HEALTHYTABLE ",null);


        return res;
    }

    public Cursor retrieveDetails (SQLiteDatabase db, String name, String dietitian)
    {
        Cursor res = db.rawQuery("select CALORIES,CARBOHYDRATES, PROTEIN, FAT from HEALTHYTABLE WHERE NAME = ? AND DIETITIAN = ?",new String[] { name,dietitian });

        return res;
    }


}