package com.example.gofishing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "stats.db";
    public static final int DBVERSION = 1;

    private SQLiteDatabase _myDB;

    public static String DBCREATE = "" +
            "CREATE TABLE stats( " +
            "ID integer PRIMARY KEY AUTOINCREMENT, " +
            "Name text not null, " +
            "DamName text not null, " +
            "DamPhoneNumber text not null, " +
            "FishVariety text not null, " +
            "FishNumber text not null " + ");" +
            "";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
        _myDB = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBCREATE);
    }

    public void insert(String Name, String DamName,
                       String DamPhoneNumber, String FishVariety, String FishNumber){
        String insertQ = "INSERT INTO stats(" +
                "Name, DamName, DamPhoneNumber, FishVariety, FishNumber" +
                ") VALUES(?, ?, ?, ?, ?)";
        _myDB.execSQL(insertQ, new Object[]{
                Name, DamName, DamPhoneNumber, FishVariety, FishNumber
        });
    }

    public void update(Stats s){
        String updateQ = "UPDATE stats SET " +
                "Name = ?, DamName = ?, DamPhoneNumber = ?, FishVariety = ?, FishNumber = ? " +
                " WHERE  ID = ? ";
        _myDB.execSQL(updateQ, new Object[]{
                s.getName(), s.getDamName(), s.getDamPhoneNumber(), s.getFishVariety(),
                s.getFishNumber(), s.getID()
        });
    }

    public void delete(Stats s){
        String deleteQ = "DELETE FROM stats WHERE ID = ?; ";
        _myDB.execSQL(deleteQ, new Object[]{s.getID()});

    }

    public List<Stats> select(){
        String selectQ = "SELECT * FROM stats ORDER BY Name; ";
        Cursor c =_myDB.rawQuery(selectQ, null);
        List<Stats> l = new ArrayList<>();
        while (c.moveToNext()){
            @SuppressLint("Range") Stats stats = new Stats(
                    c.getString(c.getColumnIndex("ID")),
                    c.getString(c.getColumnIndex("Name")),
                    c.getString(c.getColumnIndex("DamName")),
                    c.getString(c.getColumnIndex("DamPhoneNumber")),
                    c.getString(c.getColumnIndex("FishVariety")),
                    c.getString(c.getColumnIndex("FishNumber"))
            );
            l.add(stats);
        }
        return l;
    }

    public Stats selectOne(){
        String selectQ = "SELECT 1 FROM stats WHERE id=1; ";
        Cursor c =_myDB.rawQuery(selectQ, null);
        Stats l = new Stats();
        while (c.moveToNext()){
            @SuppressLint("Range") Stats stats = new Stats(
                    c.getString(c.getColumnIndex("ID")),
                    c.getString(c.getColumnIndex("Name")),
                    c.getString(c.getColumnIndex("DamName")),
                    c.getString(c.getColumnIndex("DamPhoneNumber")),
                    c.getString(c.getColumnIndex("FishVariety")),
                    c.getString(c.getColumnIndex("FishNumber"))
            );
            l = stats;
        }
        return l;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        if(i1>i){
            sqLiteDatabase.execSQL("DROP TABLE stats; ");
            sqLiteDatabase.execSQL(DBCREATE);
        }
    }
}

