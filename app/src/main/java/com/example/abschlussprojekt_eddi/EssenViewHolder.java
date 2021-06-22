package com.example.abschlussprojekt_eddi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EssenViewHolder extends RecyclerView.ViewHolder {

    private final TextView essenItemView;
    private final TextView essenEntryItemView;

    private EssenViewHolder (View itemView){
        super(itemView);
        essenItemView = itemView.findViewById(R.id.mahlzeit);
        essenEntryItemView = itemView.findViewById(R.id.entry_number);
    }

    public void bind(String essen){
        essenItemView.setText(essen);
    }

    static EssenViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_essen_tag, parent, false);
        return new EssenViewHolder(view);
    }
}
