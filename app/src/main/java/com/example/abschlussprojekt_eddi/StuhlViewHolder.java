package com.example.abschlussprojekt_eddi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class StuhlViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvUhrzeit;
    private final ImageView ivBristol;
    private final TextView tvFarbe;
    private final TextView tvSchmerz;

    private StuhlViewHolder (View itemview){
        super(itemview);
        tvUhrzeit = itemview.findViewById(R.id.textview_uhrzeit);
        ivBristol = itemview.findViewById(R.id.bristol_symbol);
        tvFarbe = itemview.findViewById(R.id.stuhl_farbe);
        tvSchmerz = itemview.findViewById(R.id.stuhl_schmerz);
    }

    public void bind(String uhrzeit, String bristol, String farbe, String schmerz){
        tvUhrzeit.setText(uhrzeit);
        getBirstolSymbol(bristol);
        tvFarbe.setText(tvFarbe.getText().toString() + farbe);
        tvSchmerz.setText(tvSchmerz.getText().toString() + schmerz);
    }

    static StuhlViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_stuhl_tag, parent, false);
        return new StuhlViewHolder(view);
    }

    public void getBirstolSymbol(String bristol){
        int bristolStufe = Integer.parseInt(bristol);
        int imageResource = 0;
        switch (bristolStufe) {

            case 1:
                ivBristol.setImageResource(R.drawable.type01);
                break;
            case 2:
                ivBristol.setImageResource(R.drawable.type02);;
                break;
            case 3:
                ivBristol.setImageResource(R.drawable.type03);;
                break;
            case 4:
                ivBristol.setImageResource(R.drawable.type04);;
                break;
            case 5:
                ivBristol.setImageResource(R.drawable.type05);;
                break;
            case 6:
                ivBristol.setImageResource(R.drawable.type06);;
                break;
            case 7:
                ivBristol.setImageResource(R.drawable.type07);
                break;
        }
    }
}
