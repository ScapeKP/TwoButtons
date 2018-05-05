package ru.smi.twobuttons;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemClickListener implements ListView.OnItemClickListener {

    public String[] Titles = {"Все комбинации",
            "Создать новую",
            "Библиотека комбинаций",
            "Настройки"};


    private Context mcontext;
    Intent gts;

    DrawerItemClickListener(Context context) {
        mcontext=context;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position==0) {
            //Все рецепты
            gts = new Intent(mcontext, AllCombo.class);
        } else if (position==1) {
            // Новый рецепт
            gts = new Intent(mcontext, NewCombo.class);
        } else if (position==2) {
            // Поиск по ингредиентам
            gts = new Intent(mcontext, Library.class);
        } else if (position==3) {
            System.out.println("переход в настройки");
            gts = new Intent(mcontext, AllCombo.class);
        } else {
            System.out.println("где я?");
        };
        mcontext.startActivity(gts);
    }

}
