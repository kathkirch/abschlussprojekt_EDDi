package com.example.abschlussprojekt_eddi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SpinnerAdapterBristol extends ArrayAdapter<BristolItem> {

    public SpinnerAdapterBristol(Context context, ArrayList<BristolItem> types){
        super(context, 0, types);
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

    //View für jede einzelne Zeile im Spinner mit Bild + Text
    //position gibt an, welche Spinner-Zeile man ausgewählt hat (beginnt mit 0)
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dropdown_item_bristol, parent, false
            );
        }

        //textView und ImageView aus dem Layout "dropdown_item_bristol"
        TextView types = convertView.findViewById(R.id.text_bristol);
        ImageView imgBristol = convertView.findViewById(R.id.img_bristol);

        BristolItem currentItem = getItem(position);

        imgBristol.setImageResource(currentItem.getImg_bristol());
        types.setText(currentItem.getDescription_bristol_type());

        return convertView;
    }
}
