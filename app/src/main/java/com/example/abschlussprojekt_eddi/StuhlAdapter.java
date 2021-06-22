package com.example.abschlussprojekt_eddi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StuhlAdapter extends RecyclerView.Adapter <StuhlAdapter.NoteHolder>{

    private List<Entity_Stuhl> stuhlNotes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_stuhl_tag, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Entity_Stuhl currentNote = stuhlNotes.get(position);
        holder.tvUhrzeit.setText(String.valueOf(currentNote.getStunde() +":"
                                + currentNote.getMinute()));
        holder.tvBristol.setText(currentNote.getBristol());
        holder.tvFarbe.setText(currentNote.getFarbe());
        holder.tvSchmerz.setText(Boolean.valueOf(currentNote.getSchmerzen()).toString());
    }

    @Override
    public int getItemCount() {
        return stuhlNotes.size();
    }

    public void setStuhlNotes(List<Entity_Stuhl> stuhlNotes){
        this.stuhlNotes = stuhlNotes;
        notifyDataSetChanged();
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView tvBristol;
        private TextView tvUhrzeit;
        private TextView tvSchmerz;
        private TextView tvFarbe;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            tvBristol = itemView.findViewById(R.id.bristol_symbol);
            tvUhrzeit = itemView.findViewById(R.id.textview_uhrzeit);
            tvSchmerz = itemView.findViewById(R.id.stuhl_schmerz);
            tvFarbe = itemView.findViewById(R.id.stuhl_farbe);
        }
    }
}
