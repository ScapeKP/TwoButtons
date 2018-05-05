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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.net.Uri;

import org.w3c.dom.Text;

import java.io.File;

public class AllCombo extends AppCompatActivity {

    static final int AUDIO_GALLERY_REQUEST_CODE = 1;


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

        // если рецептов нет, то меняем заголовок
        if (list_cursor.getCount()==0) {
            mytext.setText("Нет комбинаций");
            mytext.setVisibility(View.VISIBLE);
        } else {
            mytext.setText("");
            mytext.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_combo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gts = new Intent(AllCombo.this, NewCombo.class);
                startActivity(gts);
            }
        });


        drawermenu();



        /*
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("android.resource://ru.smi.twobuttons/raw");
        intent.setData(uri);
        startActivity(intent);
*/
/*
        // перенаправляем сразу на Combo
        Intent gts = new Intent(AllCombo.this, Combo.class);
        startActivity(gts);
*/




        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        dbHelper.onCreate(sqLiteDatabase);

        lvMain=(ListView) findViewById(R.id.all_combo_list);
        mytext = (TextView) findViewById(R.id.all_combo_text);

        //пишем скрипт, который выводит все рецепты
        list_script = "SELECT _id, Name, Button1, Button2 "  +
                "FROM Combo r ";

        // запускаем заполнение списка
        get_combo_list(list_script);


        // настраиваем обработку кликов по именам
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
/*                System.out.println("Somebody pushed the button!");
                System.out.println(position);
                System.out.println(idid.length);
                System.out.println(nname[position]);
  */              Intent gtp = new Intent(AllCombo.this, Combo.class);
                gtp.putExtra("comboID", idid[position]); //запихиваем текст в экстра вызываемого интента
                startActivity(gtp); // вызываем интент, который откроет новый экран

            }

        });

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
    /*            System.out.println("долго коснулись!!!");
                System.out.println(pos);
                System.out.println(idid[pos]);
                System.out.println(nname[pos]);
      */          Intent gtp = new Intent(AllCombo.this, deleteCombo.class);
                gtp.putExtra("comboID", idid[pos]); //запихиваем текст в экстра вызываемого интента
                startActivity(gtp); // вызываем интент, который откроет новый экран
                return true;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
