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
                tvBristol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.type01, 0, 0, 0);
                break;
            case 2:
                tvBristol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.type02, 0, 0, 0);
                break;
            case 3:
                tvBristol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.type03, 0, 0, 0);
                break;
            case 4:
                tvBristol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.type04, 0, 0, 0);
                break;
            case 5:
                tvBristol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.type05, 0, 0, 0);
                break;
            case 6:
                tvBristol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.type06, 0, 0, 0);
                break;
            case 7:
                tvBristol.setCompoundDrawablesWithIntrinsicBounds(R.drawable.type07, 0, 0, 0);
                break;
            default:
                tvBristol.setText(tvBristol.getText() + "nichts selektiert!");
        }
    }

    /*
    public void adaptSizeOfDrawable(int imageResource){
        Drawable drawable = ContextCompat.getDrawable(context, imageResource);
        int pixelDrawableSize = (int)Math.round(tv.getLineHeight() * 0.7); // Or the percentage you like (0.8, 0.9, etc.)
        drawable.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize); // setBounds(int left, int top, int right, int bottom), in this case, drawable is a square image
        tvBristol.setCompoundDrawables(
                drawable, //left
                null, //top
                null, //right
                null //bottom
        );
    }

     */
}
