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
/*
    public EssenListAdapter (@NonNull DiffUtil.ItemCallback<Entity_Essen> diffCallback) {
        super(diffCallback);
    }

 */

    @NonNull
    @Override
    public EssenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("EssenViewHolder create");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_essen_tag, parent, false);
        return new EssenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EssenViewHolder holder, int position) {
        Entity_Essen current = essenList.get(position);
        holder.essenEntryItemView.setText(current.getEssen());
    }

    @Override
    public int getItemCount() {
        return essenList.size();
    }

    public void setEssen(List<Entity_Essen> essenList){
        this.essenList = essenList;
        notifyDataSetChanged();
    }

    //um die Position fürs Löschen zu bekommen
    public Entity_Essen getEssenAt(int position){
        return essenList.get(position);
    }

    class EssenViewHolder extends RecyclerView.ViewHolder{

        private final TextView essenItemView;
        private final TextView essenEntryItemView;

        public EssenViewHolder (View itemView){
            super(itemView);
            essenItemView = itemView.findViewById(R.id.mahlzeit);
            essenEntryItemView = itemView.findViewById(R.id.entry_number);
        }

    }
/*
    public static class EssenDiff extends DiffUtil.ItemCallback<Entity_Essen> {

        @Override
        public boolean areItemsTheSame(@NonNull Entity_Essen oldItem, @NonNull Entity_Essen newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entity_Essen oldItem, @NonNull Entity_Essen newItem) {
            return false;
        }
    }

 */

    //Interface wird später in der MainActivity implementiert
    public interface OnItemClickListener{
        void onItemClick(Entity_Stuhl stuhl);
    }

    //um die Methode onItemClick aufzurufen
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
