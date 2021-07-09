package com.example.abschlussprojekt_eddi;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class StuhlListAdapter extends ListAdapter<Entity_Stuhl, StuhlListAdapter.StuhlViewHolder> {

    private OnItemClickListener listener; //für den setOnItemClickListener

    //damit nicht alle Einträge jedes mal aktualisiert werden, sondern nur derjenige, der sich geändert hat
    //List Comparison wird in einem background thread durchgeführt, ListAdapter kümmert sich um den Abgleich im Hintergrund
    //bessere Performance bei vielen Einträgen
    public StuhlListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Entity_Stuhl> DIFF_CALLBACK = new DiffUtil.ItemCallback<Entity_Stuhl>() {
        //wenn die ID gleich ist, egal welche Werte geändert wurden
        @Override
        public boolean areItemsTheSame(@NonNull Entity_Stuhl oldItem, @NonNull Entity_Stuhl newItem) {
            return oldItem.getId() == newItem.getId();
        }
        //wenn sich am Eintrag nichts geändert hat (keine Werte wurden verändert)
        @Override
        public boolean areContentsTheSame(@NonNull Entity_Stuhl oldItem, @NonNull Entity_Stuhl newItem) {
            return oldItem.getJahr() == (newItem.getJahr()) &&
                    oldItem.getMonat() == (newItem.getMonat()) &&
                    oldItem.getTag() == (newItem.getTag()) &&
                    oldItem.getStunde() == (newItem.getStunde()) &&
                    oldItem.getMinute() == (newItem.getMinute()) &&
                    oldItem.getBristol() == (newItem.getBristol()) &&
                    oldItem.getSchmerzen() == (newItem.getSchmerzen()) &&
                    oldItem.getFarbe() == (newItem.getFarbe()) &&
                    oldItem.getUnverdauteNahrung() == (newItem.getUnverdauteNahrung()) &&
                    oldItem.getSchleim() == (newItem.getSchleim()) &&
                    oldItem.getMenge() == (newItem.getMenge()) &&
                    oldItem.getNotizen().equals(newItem.getNotizen());
        }
    };

    @NonNull
    @Override
    public StuhlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_stuhl_tag, parent, false);
        return new StuhlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StuhlViewHolder holder, int position) {
        Entity_Stuhl current = getItem(position);
        String datum = (current.getTag() + "." + current.getMonat() + "." + current.getJahr());
        String uhrzeit = (current.getStunde() + ":" + current.getMinute());
        int bristol = current.getBristol();
        int farbe = (current.getFarbe());
        String schmerz = (String.valueOf(current.getSchmerzen()));
        holder.bind(datum, uhrzeit, bristol, farbe, schmerz);
    }

    //um die Position fürs Löschen zu bekommen
    public Entity_Stuhl getStuhlAt(int position) {
        return getItem(position);
    }

    class StuhlViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvUhrzeit;
        private final ImageView ivBristol;
        private final TextView tvFarbe;
        private final TextView tvSchmerz;
        private final TextView tvDatum;

        private StuhlViewHolder(View itemview) {
            super(itemview);
            tvDatum = itemview.findViewById(R.id.textview_datum_noteStuhl);
            tvUhrzeit = itemview.findViewById(R.id.textview_uhrzeit);
            ivBristol = itemview.findViewById(R.id.bristol_symbol);
            tvFarbe = itemview.findViewById(R.id.stuhl_farbe);
            tvSchmerz = itemview.findViewById(R.id.stuhl_schmerz);

            //damit man den Eintrag anklicken kann
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }

        // um die Werte in einem RecyclerView zu speichern, bzw anzeigen zu können
        @SuppressLint("SetTextI18n")
        public void bind(String datum, String uhrzeit, int bristol, int farbe, String schmerz) {
            tvDatum.setText(datum);
            tvUhrzeit.setText(uhrzeit);
            getBristolSymbol(bristol);
            String farbString = ("Farbe: " + getFarbText(farbe));
            tvFarbe.setText(farbString);
            String schmerzString = ("Schmerzen: " + schmerz);
            tvSchmerz.setText(schmerzString);
        }

        // um das richtige Bristolsymbol zu erhalten
        public void getBristolSymbol(int bristolString) {
            switch (bristolString) {
                case 0:
                    ivBristol.setImageResource(R.drawable.type01);
                    break;
                case 1:
                    ivBristol.setImageResource(R.drawable.type02);
                    break;
                case 2:
                    ivBristol.setImageResource(R.drawable.type03);
                    break;
                case 3:
                    ivBristol.setImageResource(R.drawable.type04);
                    break;
                case 4:
                    ivBristol.setImageResource(R.drawable.type05);
                    break;
                case 5:
                    ivBristol.setImageResource(R.drawable.type06);
                    break;
                case 6:
                    ivBristol.setImageResource(R.drawable.type07);
                    break;
                default:
                    ivBristol.setImageResource(R.drawable.not_selected);
            }
        }

        // um den Eintrag der Farbe als Text anstatt einer Zahl zu erhalten
        public String getFarbText (int farbInt){
            String farbe = "farbe";
            switch (farbInt){
                case 0 :
                    farbe = "braun";
                    return farbe;
                case 1 :
                    farbe = "grün";
                    return farbe;
                case 2 :
                    farbe = "grau-lehmfarben";
                    return farbe;
                case 3 :
                    farbe = "gelb";
                    return farbe;
                case 4 :
                    farbe = "rot";
                    return farbe;
                case 5 :
                    farbe = "schwarz";
                    return farbe;
            }
            return farbe;
        }
    }

    //um später auf den Eintrag im Logbuch klicken zu können
    public interface OnItemClickListener {
        void onItemClick(Entity_Stuhl stuhl);
    }

    //eigener ClickListener erstellt, um die Methode onItemClick aufzurufen
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}


