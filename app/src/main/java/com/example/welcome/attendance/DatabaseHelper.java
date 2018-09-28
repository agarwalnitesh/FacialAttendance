package com.example.welcome.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nitesh on 23/3/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "faculty_register.db";
    public static final String TABLE_NAME = "userdetail";
    public static final String TABLE_NAME2 = "attendance";
    public static final String COL_1 = "clg_id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "course";
    public static final String COL_4 = "branch";
    public static final String COL_5 = "email";
    public static final String COL_6 = "contact";


    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME +"(clg_id TEXT PRIMARY KEY, name TEXT, course Text, branch TEXT,email TEXT,contact INTEGER )");
        db.execSQL("create table "+ TABLE_NAME2 +"(clg_id TEXT,date Text,arrival_time text,departure_time text,custom text,success text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+ TABLE_NAME);
        db.execSQL("drop table if exists "+ TABLE_NAME2);
        onCreate(db);
    }

    public boolean insertData(String clg_id,String name,String course,String branch,String email,String contact)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,clg_id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,course);
        contentValues.put(COL_4,branch);
        contentValues.put(COL_5,email);
        contentValues.put(COL_6,contact);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return  false;
        else
            return true;
    }

    public boolean insertAttendance(String clg_id,String date,String arrival_time,String departure_time,String custom,String success)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clg_id",clg_id);
        contentValues.put("date",date);
        contentValues.put("arrival_time",arrival_time);
        contentValues.put("departure_time",departure_time);
        contentValues.put("custom",custom);
        contentValues.put("success",success);
        long result = db.insert(TABLE_NAME2,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;

    }

    public boolean alreadyPresent(String clg_id,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from attendance where clg_id=? and date=? ",new String[]{clg_id,date});
        if(cursor.getCount()>0)
            return false;
        else
            return true;

    }
    public boolean alreadyDeparture(String clg_id,String date)
    {
        boolean already=true;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from attendance where clg_id=? and date=? ",new String[]{clg_id,date});
        if(cursor.getCount()>0)
        {
            if(cursor.getString(3).toString().equals("--"))
            already=false;
            else
                already=true;

            return already;
        }

        else
            return already;
    }

    public boolean updateAttendance(String clg_id,String date,String departure_time)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clg_id",clg_id);
        contentValues.put("date",date);
        contentValues.put("departure_time",departure_time);
        db.update(TABLE_NAME2,contentValues,"clg_id = ? and date = ?",new String[]{clg_id,date});
        return true;

    }

    public Boolean check_id(String clg_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from userdetail where clg_id=? ",new String[]{clg_id});
        if(cursor.getCount()>0)
            return false;
        else
            return true;

    }
    public Cursor showAttendance(String date,String success)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from attendance where date=? and success=?",new String[]{date,success});
        return cursor;
    }

    public Cursor searchAttendance(String clg_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from attendance where clg_id= '"+ clg_id+"'",null);
        return cursor;
    }
    public Cursor searchByDate(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from attendance where date= '"+ date+"'",null);
        return cursor;
    }

    public boolean updateCustom(String clg_id,String custom)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("clg_id",clg_id);
        contentValues.put("custom",custom);
        db.update(TABLE_NAME2,contentValues,"clg_id = ? ",new String[]{clg_id});
        return true;

    }

}
