package ru.smi.twobuttons;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] idname;

    public CustomList(Activity context,
                      String[] name, String[] idname) {
        super(context, R.layout.sound_library_simple_layout, name);
        this.context = context;
        this.name = name;
        this.idname = idname;


    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.sound_library_simple_layout, null, true);
        TextView writtenName = (TextView) rowView.findViewById(R.id.textView1);
        writtenName.setText(name[position]);
        final String idna = idname[position];

        // здесь еще обработка клика по кнопке, чтобы запускался плеер
        Button but = (Button) rowView.findViewById(R.id.play_sound_button);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("нажата кнопка Play");
                MediaPlayer mp = new MediaPlayer();
                try {
                    Uri u = Uri.parse("android.resource://ru.smi.twobuttons/" + idna);
                    mp.setDataSource(context, u);
                    mp.prepare();
                    mp.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rowView;
    }


}