package com.example.pipid.urmoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String UR_COST = "robot_cost";
    public static final String COST_MONEY = "cost_money";
    public static final String COST_DATE = "cost_date";
    public static final String COST_TITLE = "cost_title";

    public DatabaseHelper(Context context) {
        super(context, UR_COST, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+UR_COST+"(" +
                "id integer primary key, " +
                "cost_title varchar, " +
                "cost_date varchar, " +
                COST_MONEY + " varchar)");
    }
    public void insertCost(CostItem costBean){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COST_TITLE,costBean.costTitle);
        cv.put(COST_DATE,costBean.costDate);
        cv.put(COST_MONEY,costBean.costMoney);
        database.insert(UR_COST,null, cv);
    }

    public void removeLatestCost() {

    }

    public Cursor removeAllCost() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(UR_COST, null, null);
        return database.query(UR_COST,null,null,null,null,null, null);
    }
    public Cursor getAllCostDate(){
        SQLiteDatabase database = getWritableDatabase();
        return database.query(UR_COST,null,null,null,null,null,"" + COST_DATE+" ASC");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
