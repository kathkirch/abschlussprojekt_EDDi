package com.example.abschlussprojekt_eddi;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class StuhlListAdapter extends RecyclerView.Adapter<StuhlListAdapter.StuhlViewHolder> {

    private List<Entity_Stuhl> stuhlList = new ArrayList<>();

    /*
    public StuhlListAdapter(@NonNull DiffUtil.ItemCallback<Entity_Stuhl> diffCallback) {
        super(diffCallback);
    }
     */

    @NonNull
    @Override
    public StuhlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("StuhlViewHolder create parent");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_stuhl_tag, parent, false);
        return new StuhlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StuhlViewHolder holder, int position) {
        Entity_Stuhl current = stuhlList.get(position);
        String datum = (current.getTag() + "." + current.getMonat() + "." + current.getJahr());
        String uhrzeit = (current.getStunde() +":"+ current.getMinute());
        String bristol = current.getBristol();
        String farbe = current.getFarbe();
        String schmerz = String.valueOf(current.getSchmerzen());
        holder.bind(datum, uhrzeit, bristol, farbe, schmerz);
    }

    @Override
    public int getItemCount() {
        return stuhlList.size();
    }

    public void setStuhl(List<Entity_Stuhl> stuhlList){
        this.stuhlList = stuhlList;
        notifyDataSetChanged();
    }

    //um die Position fürs Löschen zu bekommen
    public Entity_Stuhl getStuhlAt(int position){
        return stuhlList.get(position);
    }
/*
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

 */

    class StuhlViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvUhrzeit;
        private final ImageView ivBristol;
        private final TextView tvFarbe;
        private final TextView tvSchmerz;
        private final TextView tvDatum;

        private StuhlViewHolder (View itemview){
            super(itemview);
            tvDatum = itemview.findViewById(R.id.textview_datum);
            tvUhrzeit = itemview.findViewById(R.id.textview_uhrzeit);
            ivBristol = itemview.findViewById(R.id.bristol_symbol);
            tvFarbe = itemview.findViewById(R.id.stuhl_farbe);
            tvSchmerz = itemview.findViewById(R.id.stuhl_schmerz);
        }

        public void bind(String datum, String uhrzeit, String bristol, String farbe, String schmerz){
            tvDatum.setText(datum);
            tvUhrzeit.setText(uhrzeit);
            getBristolSymbol(bristol);
            tvFarbe.setText(tvFarbe.getText().toString() + farbe);
            tvSchmerz.setText(tvSchmerz.getText().toString() + schmerz);
        }

        public void getBristolSymbol(String bristolString){
            switch (bristolString) {
                case "1":
                    ivBristol.setImageResource(R.drawable.type01);
                    break;
                case "2":
                    ivBristol.setImageResource(R.drawable.type02);
                    break;
                case "3":
                    ivBristol.setImageResource(R.drawable.type03);
                    break;
                case "4":
                    ivBristol.setImageResource(R.drawable.type04);
                    break;
                case "5":
                    ivBristol.setImageResource(R.drawable.type05);
                    break;
                case "6":
                    ivBristol.setImageResource(R.drawable.type06);
                    break;
                case "7":
                    ivBristol.setImageResource(R.drawable.type07);
                    break;
                default :
                    ivBristol.setImageResource(R.drawable.not_selected);
            }
        }

    }
}


