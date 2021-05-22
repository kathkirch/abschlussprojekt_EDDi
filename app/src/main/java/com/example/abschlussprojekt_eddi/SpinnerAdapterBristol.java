package com.example.abschlussprojekt_eddi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Array;
import java.util.List;

public class SpinnerAdapterBristol extends ArrayAdapter<String> {

    Context context;
    List<String> typesList;

    public SpinnerAdapterBristol(Context context, List<String> types){
        super(context, R.layout.selected_item_bristol, types);
        this.context = context;
        this.typesList = types;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    //View für jede einzelne Zeile im Spinner
    //View besitzt ein Bild und Text
    //positon gibt an, welche Spinner-Zeile man ausgewählt hat (beginnt mit 0)
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dropdown_item_bristol, parent, false);

        //TextView und ImageView aus dem Layout dropdown_item_bristol
        TextView types = row.findViewById(R.id.text);
        ImageView imgBristol = row.findViewById(R.id.img_bristol);

        //um die Bilder von drawable zu bekommen
        Resources res = context.getResources();
        String drawableName = typesList.get(position); //output: text Beschreibung

        //die folgenden zwei Zeilen funktionieren nur, wenn der Text und das Bild gleich heißen
        //int resId = res.getIdentifier(drawableName, "drawable", context.getPackageName());
        //Drawable drawable = ContextCompat.getDrawable(context, resId);

        //wie bekommt man das Bild passend zum text????
        Drawable drawable01 = ContextCompat.getDrawable(context, R.drawable.type01);
        Drawable drawable02 = ContextCompat.getDrawable(context, R.drawable.type02);

        Drawable [] drawables_bristol = new Drawable[] {drawable01, drawable02};

        for (int i = 0; i < drawables_bristol.length; i++) {
            imgBristol.setImageDrawable(drawables_bristol[i]);
            break;
        }
        /*
        for (Drawable dw: drawables_bristol) {
            imgBristol.setImageDrawable(dw);
        }
        */

        //Text abhängig von der Position im Spinner
        types.setText(typesList.get(position));

        return row;
    }



}
