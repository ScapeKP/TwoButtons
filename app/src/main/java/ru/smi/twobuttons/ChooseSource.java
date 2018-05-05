package ru.smi.twobuttons;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ChooseSource extends AppCompatActivity {

    static final int AUDIO_GALLERY_REQUEST_CODE = 1;

    String b;
    String path;
    String path1;
    String path2;
    String name1="";
    String name2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_source);

        Intent last_intent = getIntent(); // получаем интент, который вызвал открытие этой страницы
        b = last_intent.getStringExtra("b"); // получаем текст, который хранится в экстра нашего интента
        path1 = last_intent.getStringExtra("path1"); // получаем текст, который хранится в экстра нашего интента
        path2 = last_intent.getStringExtra("path2"); // получаем текст, который хранится в экстра нашего интента
        name1 = last_intent.getStringExtra("name1"); // получаем текст, который хранится в экстра нашего интента
        name2 = last_intent.getStringExtra("name2"); // получаем текст, который хранится в экстра нашего интента
    }

    public void mediateka(View view){
        Intent audioIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(audioIntent, AUDIO_GALLERY_REQUEST_CODE);
    }


    public void app(View view){
        Intent gtp = new Intent(ChooseSource.this, SoundLibrary.class);
        gtp.putExtra("b", b); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path1", path1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path2", path2); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name1", name1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name2", name2); //запихиваем текст в экстра вызываемого интента
        startActivity(gtp);
    }




    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if (requestCode == AUDIO_GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    path = _getRealPathFromURI(ChooseSource.this, data.getData());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Intent gtp = new Intent(ChooseSource.this, NewCombo.class);
        gtp.putExtra("b", b); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path", path); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("nname", path); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path1", path1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("path2", path2); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name1", name1); //запихиваем текст в экстра вызываемого интента
        gtp.putExtra("name2", name2); //запихиваем текст в экстра вызываемого интента
        startActivity(gtp); // вызываем интент, который откроет новый экран
    }

    private String _getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Audio.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
