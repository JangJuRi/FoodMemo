package com.example.foodmemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyFood.db";
    public static final String TABLE_NAME = "food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TYPE_TEXT = "type_text";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_REGION = "region";
    public static final String COLUMN_REGION_TEXT = "region_text";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_MEMO = "memo";
    public static final String COLUMN_PIC = "pic";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE food " + "(id integer primary key autoincrement,name varchar(20), type integer, typeT varchar(20), score integer, region integer, regionT varchar(20), phone varchar(20), address varchar(30), memo varchar(200),pic blob)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS food");
        onCreate(db);
    }

    public boolean insertFood(String name, Integer type, String typeT, Integer score, Integer region, String regionT, String phone, String address, String memo, byte[] pic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("typeT", typeT);
        contentValues.put("score", score);
        contentValues.put("region", region);
        contentValues.put("regionT", regionT);
        contentValues.put("phone", phone);
        contentValues.put("address", address);
        contentValues.put("memo", memo);
        contentValues.put("pic", pic);
        db.insert("food", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from food where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateFood(Integer id, String name, Integer type, String typeT, Integer score, Integer region, String regionT, String phone, String address, String memo, byte[] pic) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("typeT", typeT);
        contentValues.put("score", score);
        contentValues.put("region", region);
        contentValues.put("regionT", regionT);
        contentValues.put("phone", phone);
        contentValues.put("address", address);
        contentValues.put("memo", memo);
        contentValues.put("pic", pic);
        db.update("food", contentValues, "id = "+id,null);
        return true;
    }

    public Integer deleteFood(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("food",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList getAllFood() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from food", null);
        res.moveToFirst();

        while (res.isAfterLast()==false) {
            arrayList.add(res.getString(res.getColumnIndex(COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(COLUMN_NAME))+" "+
                    res.getString(res.getColumnIndex(COLUMN_TYPE))+" "+
                    res.getString(res.getColumnIndex(COLUMN_TYPE_TEXT))+" "+
                    res.getString(res.getColumnIndex(COLUMN_SCORE))+" "+
                    res.getString(res.getColumnIndex(COLUMN_REGION))+" "+
                    res.getString(res.getColumnIndex(COLUMN_REGION_TEXT))+" "+
                    res.getString(res.getColumnIndex(COLUMN_PHONE))+" "+
                    res.getString(res.getColumnIndex(COLUMN_ADDRESS))+" "+
                    res.getString(res.getColumnIndex(COLUMN_MEMO))+" "+
                    res.getString(res.getColumnIndex(COLUMN_PIC)));
            res.moveToNext();
        }
        return arrayList;
    }

    public Cursor getCursorFood() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from food", null);

        return res;
    }

    public Cursor getPic(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from food where id=" + id + "", null);
        return res;
    }
}