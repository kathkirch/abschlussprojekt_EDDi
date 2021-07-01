package com.example.abschlussprojekt_eddi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        String essen = current.getEssen();
        String datum = (current.getTag() + "." + current.getMonat() + "." + current.getJahr());
        holder.bind(essen, datum);
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
        private final TextView essen_datum;

        public EssenViewHolder (View itemView){
            super(itemView);
            essen_mahlzeit = itemView.findViewById(R.id.mahlzeit);
            essen_datum = itemView.findViewById(R.id.textView_essen_datum);
        }

        public void bind (String essen, String datum) {
            essen_mahlzeit.setText(essen);
            essen_datum.setText(datum);
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
