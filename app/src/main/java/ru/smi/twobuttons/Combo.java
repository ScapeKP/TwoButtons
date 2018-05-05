package ru.smi.twobuttons;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.InputStream;

public class Combo extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    MediaPlayer mp1;
    MediaPlayer mp2;
    String path1;
    String path2;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;



    private void drawermenu() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerItemClickListener dr = new DrawerItemClickListener(this);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dr.Titles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(dr);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        drawermenu();

        Intent last_intent = getIntent(); // получаем интент, который вызвал открытие этой страницы
        String comboID = last_intent.getStringExtra("comboID"); // получаем текст, который хранится в экстра нашего интента

        System.out.println(comboID);

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();


        String script = "SELECT _id, Name, Button1, Button2 "  +
                "FROM Combo r " +
                "where _id = " + comboID;

/*        String script = "SELECT _id, Name, Button1, Button2 "  +
                "FROM ComboLibrary r ";
  */      Cursor cursor = sqLiteDatabase.rawQuery(script, null);

       if (cursor.getCount()!=0) {
            cursor.moveToFirst();
            path1 = cursor.getString(cursor.getColumnIndex("Button1"));
            path2 = cursor.getString(cursor.getColumnIndex("Button2"));
        }

    }

    public void btn1(View v) {
        System.out.println("кнопка 1");
        if (mp2==null & mp1==null) {
            // проиграть файл
            try {
                System.out.println(path1);
                mp1 = new MediaPlayer();
            //    mp1.setDataSource(path1);
                Uri u = Uri.parse(path1);
                mp1.setDataSource(Combo.this,u);

                mp1.prepare();
                mp1.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    public void btn2(View v){




        System.out.println("кнопка 2");
        if (mp1!=null) {
            if (mp1.isPlaying()) {
                System.out.println("думает, что включен mp1");
                mp1.reset();
                //mp1.release();
            }
        }
        if (mp2==null) {
            System.out.println("думает, что mp2 выключен");
            try {
                System.out.println("Нажата вторая кнопка!");
                mp2 = new MediaPlayer();
             //   mp2.setDataSource(path2);

             //   Uri u = Uri.parse("android.resource://ru.smi.twobuttons/" + R.raw.haligali);

                Uri u = Uri.parse(path2);
                mp2.setDataSource(Combo.this,u);

                mp2.prepare();
                mp2.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (mp2.isPlaying()) {
            System.out.println("думает, что mp2 is playing");
            try {
                mp2.stop();
                mp2.prepare();
                mp2.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("ничего не думает");
    }

}
