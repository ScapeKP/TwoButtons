package ru.smi.twobuttons;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddToAllCombo extends AppCompatActivity {

    Intent last_intent;
    String comboID;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_all_combo);

        last_intent = getIntent(); // получаем интент, который вызвал открытие этой страницы
        comboID = last_intent.getStringExtra("comboID"); // получаем текст, который хранится в экстра нашего интента

        this.setTitle("");
    }

    public void add(View v) {
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        String script = "insert into Combo (name, button1, button2)" +
                "select name, button1, button2 FROM ComboLibrary " +
                "where _id = " + comboID;
        sqLiteDatabase.execSQL(script);

        Intent gtp = new Intent(AddToAllCombo.this, AllCombo.class);
        startActivity(gtp);
    }
}
