package ru.smi.twobuttons;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Library extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView lvMain;
    TextView mytext;
    String list_script;
    Cursor list_cursor;
    SimpleCursorAdapter list_adapter;
    String[] idid;
    String[] nname;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    Toolbar toolbar;



    private void drawermenu() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerItemClickListener dr = new DrawerItemClickListener(this);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dr.Titles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(dr);

    }


    public void get_combo_list(String script){

        list_cursor = sqLiteDatabase.rawQuery(script, null);
        String[] from = {"Name"};
        int[] to = new int[]{R.id.textView1};
        list_adapter = new SimpleCursorAdapter(this, R.layout.allcombo_simple_layout, list_cursor, from, to);
        lvMain.setAdapter(list_adapter);
        idid = DBHelper.getalldata_fromcursor(list_cursor,0);
        nname = DBHelper.getalldata_fromcursor(list_cursor,1);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawermenu();

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        dbHelper.onCreate(sqLiteDatabase);

        lvMain=(ListView) findViewById(R.id.all_combo_list);
        mytext = (TextView) findViewById(R.id.all_combo_text);

        //пишем скрипт, который выводит все рецепты
        list_script = "SELECT _id, Name, Button1, Button2 "  +
                "FROM ComboLibrary r ";

        // запускаем заполнение списка
        get_combo_list(list_script);


        // настраиваем обработку кликов по именам
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent gtp = new Intent(Library.this, Combo.class);
                gtp.putExtra("comboID", idid[position]); //запихиваем текст в экстра вызываемого интента
                startActivity(gtp); // вызываем интент, который откроет новый экран

            }

        });

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Intent gtp = new Intent(Library.this, AddToAllCombo.class);
                gtp.putExtra("comboID", idid[pos]); //запихиваем текст в экстра вызываемого интента
                startActivity(gtp); // вызываем интент, который откроет новый экран

                /*
                Intent gtp = new Intent(Library.this, deleteCombo.class);
                gtp.putExtra("comboID", idid[pos]); //запихиваем текст в экстра вызываемого интента
                startActivity(gtp); // вызываем интент, который откроет новый экран
                */
                return true;
            }
        });



    }

}
