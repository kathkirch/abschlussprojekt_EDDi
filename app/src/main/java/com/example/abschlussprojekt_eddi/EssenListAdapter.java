package com.example.abschlussprojekt_eddi;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EssenListAdapter extends ListAdapter <Entity_Essen, EssenViewHolder> {
    private OnItemClickListener listener;
    private ArrayList<Entity_Essen> essenList = new ArrayList<>();

    public EssenListAdapter (@NonNull DiffUtil.ItemCallback<Entity_Essen> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EssenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        System.out.println("EssenViewHolder create");
        return EssenViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EssenViewHolder holder, int position) {
        Entity_Essen current = getItem(position);
        holder.bind(current.getEssen());
    }

    //um die Position fürs Löschen zu bekommen
    public Entity_Essen getEssenAt(int position){
        return essenList.get(position);
    }

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

    //Interface wird später in der MainActivity implementiert
    public interface OnItemClickListener{
        void onItemClick(Entity_Stuhl stuhl);
    }

    //um die Methode onItemClick aufzurufen
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
