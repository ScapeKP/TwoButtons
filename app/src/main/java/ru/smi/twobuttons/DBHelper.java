package ru.smi.twobuttons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Марина on 14.05.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "twobuttonsDB";

    public static final String ID = "_id";
    public static final String ComboTable = "Combo";
    public static final String Name = "Name";
    public static final String Button1 = "Button1";
    public static final String Button2 = "Button2";
    public static final String CreateDate = "CreateDate";

    public static final String ComboLibraryTable = "ComboLibrary";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + ComboTable + "(" + ID + " integer primary key,"
                + Name + " text,"
                + Button1 + " text,"
                + Button2 + " text,"
                + CreateDate + " datetime" + ")");

        db.execSQL("drop table if exists ComboLibrary");
        db.execSQL("create table IF NOT EXISTS " + ComboLibraryTable + "(" + ID + " integer primary key,"
                + Name + " text,"
                + Button1 + " text,"
                + Button2 + " text"  + ")");
        ContentValues values = new ContentValues();
        values.put("Name", "Тест1");
        values.put("Button1", "android.resource://ru.smi.twobuttons/2131165185");
        values.put("Button2", "android.resource://ru.smi.twobuttons/2131165184");
        db.insert(
                "ComboLibrary",
                null,
                values);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ComboTable);
        onCreate(db);

    }

    public void deleteBase(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + ComboTable);
    }

    public static String[] getalldata_fromcursor(Cursor cursor, Integer column) {

        System.out.println("заущен метод");
        ArrayList<String> myArrayList = new ArrayList<>();

        cursor.moveToFirst();
        myArrayList.add(cursor.getString(column));


        // получаем все данные из базы
        while(cursor.moveToNext()){
            myArrayList.add(cursor.getString(column));
        }
        // конвертируем ArrayList в массив
        String[] all_data = {};
        all_data = myArrayList.toArray(new String[myArrayList.size()]);
        return all_data;
    }

}
