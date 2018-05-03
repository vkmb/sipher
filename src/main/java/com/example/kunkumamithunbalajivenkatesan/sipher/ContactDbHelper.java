package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ContactDbHelper extends SQLiteOpenHelper {
     private static final String DATABASE_NAME = "message.db";
     private static final int VERSION = 1;
     private static final String CREATE_TABLE = "create table "+
             ContactContract.ContactEntry.TABLE_NAME
            +"("+ContactContract.ContactEntry.TIME_DATE +
             " long,"+ContactContract.ContactEntry.SHIFT+
             " TEXT,"+ContactContract.ContactEntry.MESSAGE+" TEXT);";
    private static final String DROP_TABLE = "drop table if exists "
            + ContactContract.ContactEntry.TABLE_NAME;
    private static final String DROP_TABLE2 = "drop table if exists "
            + ContactContract.ContactEntry.TABLE_NAME2;

    private static final String CREATE_TABLE2 = "create table "
            + ContactContract.ContactEntry.TABLE_NAME2
            +"("+ContactContract.ContactEntry.lol +" text);";

    ContactDbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE2);
        ContentValues cv = new ContentValues();
        cv.put(ContactContract.ContactEntry.lol , "0000");
        sqLiteDatabase.insert(ContactContract.ContactEntry.TABLE_NAME2, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(DROP_TABLE2);

        onCreate(sqLiteDatabase);
    }

     void addMessage(long time, String shift, String message, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(ContactContract.ContactEntry.TIME_DATE , time);
        cv.put(ContactContract.ContactEntry.SHIFT , shift);
        cv.put(ContactContract.ContactEntry.MESSAGE , message);
        db.insert(ContactContract.ContactEntry.TABLE_NAME, null, cv);
    }

     Cursor readDb(SQLiteDatabase sqLiteDatabase){
        String[] projections = {ContactContract.ContactEntry.TIME_DATE,
                ContactContract.ContactEntry.SHIFT, ContactContract.ContactEntry.MESSAGE};
         return sqLiteDatabase.query(ContactContract.ContactEntry.TABLE_NAME, projections,
                 null,null,null,null,null);
    }

     void deleteDB(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("delete from "+ ContactContract.ContactEntry.TABLE_NAME);
    }

     void changeLol(SQLiteDatabase sqLiteDatabase, String old, String new1){
        ContentValues cv = new ContentValues();
        cv.put(ContactContract.ContactEntry.lol, new1);
        String selection = ContactContract.ContactEntry.lol+" ='"+ old+"'";
        sqLiteDatabase.update(ContactContract.ContactEntry.TABLE_NAME2, cv, selection, null);
    }
     Cursor readDB(SQLiteDatabase sqLiteDatabase){
        String[] projections = {ContactContract.ContactEntry.lol};
         return sqLiteDatabase.query(ContactContract.ContactEntry.TABLE_NAME2,projections,
                 null,null,null,null,null);
    }
}
