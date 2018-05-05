package ru.smi.twobuttons;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SoundLibrary extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView lvMain;
    TextView mytext;
    String list_script;
    Cursor list_cursor;
    CustomList list_adapter;
    String[] idname  = {};
    String[] nname  = {};
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    Toolbar toolbar;
    String b;
    String path1="";
    String path2="";
    String name1="";
    String name2="";

    private void drawermenu() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerItemClickListener dr = new DrawerItemClickListener(this);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dr.Titles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(dr);

    }

    public void get_sound_list(){

        ArrayList<String> nameArray = new ArrayList<>();
        ArrayList<String> idArray = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        // loop for every file in raw folder
        for(int count=0; count < fields.length; count++){
            // Use that if you just need the file name
            String filename = fields[count].getName();
            nameArray.add(filename);
            Resources res = this.getResources();
            int soundId = res.getIdentifier(filename, "raw", this.getPackageName());
            idArray.add(Integer.toString(soundId));
        }

        // конвертируем Array в массив
        nname = nameArray.toArray(new String[nameArray.size()]);
        idname = idArray.toArray(new String[idArray.size()]);

        list_adapter = new CustomList(SoundLibrary.this, nname,idname);
        lvMain.setAdapter(list_adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_library);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent last_intent = getIntent(); // получаем интент, который вызвал открытие этой страницы
        b = last_intent.getStringExtra("b"); // получаем текст, который хранится в экстра нашего интента
        path1 = last_intent.getStringExtra("path1"); // получаем текст, который хранится в экстра нашего интента
        path2 = last_intent.getStringExtra("path2"); // получаем текст, который хранится в экстра нашего интента
        name1 = last_intent.getStringExtra("name1"); // получаем текст, который хранится в экстра нашего интента
        name2 = last_intent.getStringExtra("name2"); // получаем текст, который хранится в экстра нашего интента


        drawermenu();

        lvMain=(ListView) findViewById(R.id.sound_library_list);

        // запускаем заполнение списка
        get_sound_list();


        // настраиваем обработку кликов по именам
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                System.out.println("выбрали мелодию из библиотеки");
/*                System.out.println(position);
                System.out.println(idid.length);
                System.out.println(nname[position]);
  */              Intent gtp = new Intent(SoundLibrary.this, NewCombo.class);
                gtp.putExtra("b", b); //запихиваем текст в экстра вызываемого интента
                gtp.putExtra("path1", path1); //запихиваем текст в экстра вызываемого интента
                gtp.putExtra("path2", path2); //запихиваем текст в экстра вызываемого интента
                gtp.putExtra("name1", name1); //запихиваем текст в экстра вызываемого интента
                gtp.putExtra("name2", name2); //запихиваем текст в экстра вызываемого интента
                gtp.putExtra("nname", nname[position]); //запихиваем текст в экстра вызываемого интента
                gtp.putExtra("path", "android.resource://ru.smi.twobuttons/" + idname[position]); //запихиваем текст в экстра вызываемого интента
                startActivity(gtp); // вызываем интент, который откроет новый экран

            }

        });


    }

}
