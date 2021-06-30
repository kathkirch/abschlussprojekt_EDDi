package com.example.abschlussprojekt_eddi;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


public class StuhlListAdapter extends ListAdapter <Entity_Stuhl, StuhlViewHolder> {

    public StuhlListAdapter(@NonNull DiffUtil.ItemCallback<Entity_Stuhl> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public StuhlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("StuhlViewHolder create parent");
        return StuhlViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull StuhlViewHolder holder, int position) {
        Entity_Stuhl current = getItem(position);
        String datum = (current.getTag() + "." + current.getMonat() + "." + current.getJahr());
        String uhrzeit = (current.getStunde() +":"+ current.getMinute());
        String bristol = current.getBristol();
        String farbe = current.getFarbe();
        String schmerz = String.valueOf(current.getSchmerzen());

        holder.bind(datum, uhrzeit, bristol, farbe, schmerz);
    }

    public static class StuhlDiff extends DiffUtil.ItemCallback<Entity_Stuhl> {

        @Override
        public boolean areItemsTheSame(@NonNull Entity_Stuhl oldItem, @NonNull Entity_Stuhl newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entity_Stuhl oldItem, @NonNull Entity_Stuhl newItem) {
            return false;
        }
    }
}


