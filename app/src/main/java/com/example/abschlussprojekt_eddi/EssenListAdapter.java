package com.example.abschlussprojekt_eddi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EssenListAdapter extends RecyclerView.Adapter <EssenListAdapter.EssenViewHolder> {

    private OnItemClickListener listener;
    private List<Entity_Essen> essenList = new ArrayList<>();

    @NonNull
    @Override
    public EssenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_essen_tag, parent, false);
        return new EssenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EssenViewHolder holder, int position) {
        Entity_Essen current = essenList.get(position);
        holder.essen_mahlzeit.setText(current.getEssen());
        //holder.essen_entryNumber.setText(current.getEssenID());
    }

    @Override
    public int getItemCount() {
        return essenList.size();
    }

    public void setEssen(List<Entity_Essen> essenList){
        this.essenList = essenList;
        notifyDataSetChanged(); //wird später geändert!
    }

    //um die Position fürs Löschen zu bekommen
    public Entity_Essen getEssenAt(int position){
        return essenList.get(position);
    }

    class EssenViewHolder extends RecyclerView.ViewHolder{

        private final TextView essen_mahlzeit;
        private final TextView essen_entryNumber;

        public EssenViewHolder (View itemView){
            super(itemView);
            essen_mahlzeit = itemView.findViewById(R.id.mahlzeit);
            essen_entryNumber = itemView.findViewById(R.id.entry_number);
        }
    }

    //Interface wird später in der MainActivity implementiert
    public interface OnItemClickListener{
        void onItemClick(Entity_Stuhl stuhl);
    }

    //um die Methode onItemClick aufzurufen
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
