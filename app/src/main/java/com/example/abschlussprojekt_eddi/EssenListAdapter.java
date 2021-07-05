package com.example.abschlussprojekt_eddi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EssenListAdapter extends ListAdapter<Entity_Essen, EssenListAdapter.EssenViewHolder> {

    private OnItemClickListener listener; //für den setOnItemClickListener

    //damit nicht alle Einträge jedes mal aktualisiert werden, sondern nur derjenige, der sich geändert hat
    //bessere Performance bei vielen Einträgen
    public EssenListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Entity_Essen> DIFF_CALLBACK = new DiffUtil.ItemCallback<Entity_Essen>() {
        //wenn die ID gleich ist, egal welche Werte geändert wurden
        @Override
        public boolean areItemsTheSame(@NonNull Entity_Essen oldItem, @NonNull Entity_Essen newItem) {
            return oldItem.getEssenID() == newItem.getEssenID();
        }

        //wenn sich am Eintrag nichts geändert hat (keine Werte wurden verändert)
        @Override
        public boolean areContentsTheSame(@NonNull Entity_Essen oldItem, @NonNull Entity_Essen newItem) {
            return oldItem.getEssen().equals(newItem.getEssen()) &&
                    oldItem.getJahr() == (newItem.getJahr()) &&
                    oldItem.getMonat() == (newItem.getMonat()) &&
                    oldItem.getTag() == (newItem.getTag()) &&
                    oldItem.getStunde() == (newItem.getStunde()) &&
                    oldItem.getMinute() == (newItem.getMinute());
        }
    };

    @NonNull
    @Override
    public EssenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_essen_tag, parent, false);
        return new EssenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EssenViewHolder holder, int position) {
        Entity_Essen current = getItem(position);
        String essen = current.getEssen();
        String datum = (current.getTag() + "." + current.getMonat() + "." + current.getJahr());
        holder.bind(essen, datum);
    }


    //um die Position fürs Löschen zu bekommen
    public Entity_Essen getEssenAt(int position) {
        return getItem(position);
    }

    class EssenViewHolder extends RecyclerView.ViewHolder {

        private final TextView essen_mahlzeit;
        private final TextView essen_datum;

        public EssenViewHolder (View itemView){
            super(itemView);
            essen_mahlzeit = itemView.findViewById(R.id.mahlzeit);
            essen_datum = itemView.findViewById(R.id.textView_essen_datum);

            //damit man den Eintrag anklicken kann
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }

        public void bind (String essen, String datum) {
            essen_mahlzeit.setText(essen);
            essen_datum.setText(datum);
        }

    }

    //Interface wird später in der MainActivity implementiert
    public interface OnItemClickListener {
        void onItemClick(Entity_Essen essen);
    }

    //eigener ClickListener erstellt, um die Methode onItemClick aufzurufen
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
