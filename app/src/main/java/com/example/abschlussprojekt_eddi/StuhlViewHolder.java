package com.example.abschlussprojekt_eddi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class StuhlViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvUhrzeit;
    private final TextView tvBristol;
    private final TextView tvFarbe;
    private final TextView tvSchmerz;

    private StuhlViewHolder (View itemview){
        super(itemview);
        tvUhrzeit = itemview.findViewById(R.id.textview_uhrzeit);
        tvBristol = itemview.findViewById(R.id.bristol_symbol);
        tvFarbe = itemview.findViewById(R.id.stuhl_farbe);
        tvSchmerz = itemview.findViewById(R.id.stuhl_schmerz);
    }

    public void bind(String uhrzeit, String bristol, String farbe, String schmerz){
        tvUhrzeit.setText(uhrzeit);
        tvBristol.setText(bristol);
        tvFarbe.setText(farbe);
        tvSchmerz.setText(schmerz);
    }

    static StuhlViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_stuhl_tag, parent, false);
        return new StuhlViewHolder(view);
    }
}
