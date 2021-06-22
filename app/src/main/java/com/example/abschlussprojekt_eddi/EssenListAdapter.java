package com.example.abschlussprojekt_eddi;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class EssenListAdapter extends ListAdapter <Entity_Essen, EssenViewHolder> {

    public EssenListAdapter (@NonNull DiffUtil.ItemCallback<Entity_Essen> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EssenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EssenViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EssenViewHolder holder, int position) {
        Entity_Essen current = getItem(position);
        holder.bind(current.getEssen());
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
}
