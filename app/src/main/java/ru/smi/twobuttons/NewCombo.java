package ru.smi.twobuttons;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.R.attr.data;

public class NewCombo extends AppCompatActivity {

    static final int AUDIO_GALLERY_REQUEST_CODE = 1;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    String path;
    String path1="";
    String path2="";
    String b;
    String name1="";
    String name2="";
    String nname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_combo);

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        Intent last_intent = getIntent(); // получаем интент, который вызвал открытие этой страницы
        // b и path - новая, только что полученная инфа
        b = last_intent.getStringExtra("b"); // получаем текст, который хранится в экстра нашего интента
        path = last_intent.getStringExtra("path"); // получаем текст, который хранится в экстра нашего интента
        nname = last_intent.getStringExtra("nname"); // получаем текст, который хранится в экстра нашего интента
        // path1 и path2 - данные, полученные в предыдущие нажатия кнопок
        path1 = last_intent.getStringExtra("path1"); // получаем текст, который хранится в экстра нашего интента
        path2 = last_intent.getStringExtra("path2"); // получаем текст, который хранится в экстра нашего интента
        name1 = last_intent.getStringExtra("name1"); // получаем текст, который хранится в экстра нашего интента
        name2 = last_intent.getStringExtra("name2"); // получаем текст, который хранится в экстра нашего интента

        if (b!=null) {
            if (b.equals("1")) {
                path1=path;
                name1=nname;
            } else if (b.equals("2")) {
                path2=path;
                name2=nname;
            }
        }

        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(name1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        text2.setText(name2);

    }

/*
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if (requestCode == AUDIO_GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    path = _getRealPathFromURI(NewCombo.this, data.getData());

                    if (b==1) {
                        path1=path;
                        TextView text1 = (TextView) findViewById(R.id.text1);
                        text1.setText(path1);

                    }else if (b==2) {
                        path2=path;
                        TextView text2 = (TextView) findViewById(R.id.text2);
                        text2.setText(path2);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String _getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Audio.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    */
    public void btn1(View v) {
        b="1";
        Intent gtp = new Intent(NewCombo.this, ChooseSource.class);
        gtp.putExtra("b", b); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path1", path1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path2", path2); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name1", name1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name2", name2); //запихиваем текст в экстра вызываемого интента

        startActivity(gtp);

        //Intent audioIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
        //startActivityForResult(audioIntent, AUDIO_GALLERY_REQUEST_CODE);
    }


    public void btn2(View v){
        b="2";
        Intent gtp = new Intent(NewCombo.this, ChooseSource.class);
        gtp.putExtra("b", b); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path1", path1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path2", path2); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name1", name1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name2", name2); //запихиваем текст в экстра вызываемого интента

        startActivity(gtp);

        //Intent audioIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(audioIntent, AUDIO_GALLERY_REQUEST_CODE);

    }

    public void create_combo(View v){
        // сохраняем путь к файлу (предполагается, что отработал блок OnActivityResult)
        EditText name = (EditText) findViewById(R.id.new_name);
        String n = name.getText().toString();
        if (path1.equals("") | path2.equals("") | n.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Не все данные введены", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            ContentValues values = new ContentValues();
            values.put("Name", name.getText().toString());
            values.put("Button1", path1);
            values.put("Button2", path2);
            Calendar c = Calendar.getInstance();
            values.put("CreateDate", c.getFirstDayOfWeek());

            long comboID = sqLiteDatabase.insert(
                    "Combo",
                    null,
                    values);

            // переходим на страницу только что созданного комбо
            Intent gts = new Intent(NewCombo.this, AllCombo.class);
            //gts.putExtra("comboID", Long.toString(comboID)); //запихиваем текст в экстра вызываемого интента
            startActivity(gts);
        }

    }
}
