package com.example.abschlussprojekt_eddi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abschlussprojekt_eddi.BenutzerdatenSpeicher;
import com.example.abschlussprojekt_eddi.Einstellungen;
import com.example.abschlussprojekt_eddi.Eintrag_Essen;
import com.example.abschlussprojekt_eddi.Eintrag_Stuhl;
import com.example.abschlussprojekt_eddi.Entity_Essen;
import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.EssenListAdapter;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.StuhlListAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Essen;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

import java.util.List;

import static com.example.abschlussprojekt_eddi.R.id;
import static com.example.abschlussprojekt_eddi.R.layout;

public class Startseite_Fragment extends Fragment implements View.OnClickListener {

    public ViewModel_Stuhl viewModel_stuhl;
    public ViewModel_Essen viewModel_essen;

    Intent intentStuhl;
    Intent intentEssen;
    Intent intentEinstellungen;

    ImageButton bV1;
    ImageButton bV2;
    ImageButton bV3;
    ImageButton bV4;
    ImageButton bV5;

    public static final int NEW_STUHL_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_STUHL_EDIT_REQUEST_CODE = 11;
    public static final int NEW_ESSEN_ACTIVITY_REQUEST_CODE = 2;
    public static final int NEW_ESSEN_EDIT_REQUEST_CODE = 22;

    BenutzerdatenSpeicher bdsp;

    public Startseite_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(layout.fragment_startseite_, container, false);

        bV1 = view.findViewById(id.VAS_0);
        bV2 = view.findViewById(id.VAS_1);
        bV3 = view.findViewById(id.VAS_2);
        bV4 = view.findViewById(id.VAS_3);
        bV5 = view.findViewById(id.VAS_4);

        bdsp = new BenutzerdatenSpeicher(getContext());

        setStimmungBackground();

        //Recyclerview für Essen und Stuhl erstellen
        RecyclerView recyclerView1 = (RecyclerView) view.findViewById(id.recycler_view_startseite_essen);
        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(id.recycler_view_startseite_stuhl);

        //Recycler View für Essen
        EssenListAdapter adapter = new EssenListAdapter();
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        //ViewModel für Essen
        viewModel_essen = new ViewModelProvider(getActivity()).get(ViewModel_Essen.class);
        viewModel_essen.getAllEssen().observe(getViewLifecycleOwner(), new Observer<List<Entity_Essen>>() {
            @Override
            //um den Adapter zu aktualisieren
            public void onChanged(List<Entity_Essen> entity_essens) {
                adapter.submitList(entity_essens);
                //entity_essens.clear();
            }
        });

        //macht den RecyclerView wischbar (für Essen-Löschung)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                //Löschen bei Rechts-Wischen
                ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    viewModel_essen.deleteEssen(adapter.getEssenAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Essen Eintrag gelöscht", Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }).attachToRecyclerView(recyclerView1);

        //um auf den Essen-Eintrag zu klicken und ihn zu ändern
        //Daten aus dem Eintrag werden übergeben
        adapter.setOnItemClickListener(new EssenListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Entity_Essen essen) {
                Intent intent = new Intent(getContext(), Eintrag_Essen.class);
                intent.putExtra(Eintrag_Essen.EXTRA_ESSEN_ID, essen.getEssenID());
                intent.putExtra(Eintrag_Essen.EXTRA_ESSEN, essen.getEssen());
                intent.putExtra(Eintrag_Essen.EXTRA_UHRZEIT, essen.getStunde()); //muss noch geändert werden!!
                intent.putExtra(Eintrag_Essen.EXTRA_DATUM, essen.getJahr()); //muss noch geändert werden!!
                getActivity().startActivityForResult(intent, NEW_ESSEN_EDIT_REQUEST_CODE); //startet Methode in der MainActivity
            }
        });

        //RecyclerView für Stuhl
        StuhlListAdapter stuhlAdapter = new StuhlListAdapter();
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(stuhlAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        //ViewModel für Stuhl
        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        viewModel_stuhl.getAllStuhl().observe(getViewLifecycleOwner(), new Observer<List<Entity_Stuhl>>() {
            @Override
            public void onChanged(List<Entity_Stuhl> entity_stuhl) {
                stuhlAdapter.submitList(entity_stuhl);
                //entity_stuhl.clear();
            }
        });

        //macht den RecyclerView wischbar (für Stuhl-Löschung)
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                //Löschen bei Rechts-Wischen
                ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    viewModel_stuhl.delete(stuhlAdapter.getStuhlAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getActivity(), "Stuhl Eintrag gelöscht", Toast.LENGTH_SHORT).show();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }).attachToRecyclerView(recyclerView2);

        //Daten aus dem Stuhl Eintrag werden übergeben
        stuhlAdapter.setOnItemClickListener(new StuhlListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Entity_Stuhl stuhl) {
                Intent intent = new Intent(getContext(), Eintrag_Stuhl.class);
                intent.putExtra(Eintrag_Stuhl.EXTRA_ID, stuhl.getId());
                intent.putExtra(Eintrag_Stuhl.EXTRA_DATUM, stuhl.getMonat()); //muss noch geändert werden
                intent.putExtra(Eintrag_Stuhl.EXTRA_UHRZEIT, stuhl.getMinute()); //muss noch geändert werden
                intent.putExtra(Eintrag_Stuhl.EXTRA_BRISTOL, stuhl.getBristol());
                intent.putExtra(Eintrag_Stuhl.EXTRA_BLUT, stuhl.getBlut());
                intent.putExtra(Eintrag_Stuhl.EXTRA_SCHMERZ, stuhl.getSchmerzen());
                intent.putExtra(Eintrag_Stuhl.EXTRA_FARBE, stuhl.getFarbe());
                intent.putExtra(Eintrag_Stuhl.EXTRA_UNVERDAUTENAHRUNG, stuhl.getUnverdauteNahrung());
                intent.putExtra(Eintrag_Stuhl.EXTRA_SCHLEIM, stuhl.getSchleim());
                intent.putExtra(Eintrag_Stuhl.EXTRA_MENGE, stuhl.getMenge());
                intent.putExtra(Eintrag_Stuhl.EXTRA_NOTIZ, stuhl.getNotizen());
                getActivity().startActivityForResult(intent, NEW_STUHL_EDIT_REQUEST_CODE); //startet Methode in der MainActivity
            }
        });

        Button btStuhl = view.findViewById(id.stuhl_button);
        Button btEssen = view.findViewById(id.essen_button);
        Button btEinstellungen = view.findViewById(id.einstellungenButton);

        bV1.setOnClickListener(this::stimmung);
        bV2.setOnClickListener(this::stimmung);
        bV3.setOnClickListener(this::stimmung);
        bV4.setOnClickListener(this::stimmung);
        bV5.setOnClickListener(this::stimmung);

        btStuhl.setOnClickListener(this::onClick);
        btEssen.setOnClickListener(this::onClick);
        btEinstellungen.setOnClickListener(this::onClick);



        return view;
    }

    public void stimmung(View view) {
        switch (view.getId()) {
            case id.VAS_0:
                bdsp.stimmungSpeichern("ausgezeichnet");
                bV1.setBackgroundResource(R.drawable.frame_selected);
                bV2.setBackgroundResource(R.drawable.background_transparent);
                bV3.setBackgroundResource(R.drawable.background_transparent);
                bV4.setBackgroundResource(R.drawable.background_transparent);
                bV5.setBackgroundResource(R.drawable.background_transparent);
                break;
            case id.VAS_1:
                bdsp.stimmungSpeichern("gut");
                bV2.setBackgroundResource(R.drawable.frame_selected);
                bV1.setBackgroundResource(R.drawable.background_transparent);
                bV3.setBackgroundResource(R.drawable.background_transparent);
                bV4.setBackgroundResource(R.drawable.background_transparent);
                bV5.setBackgroundResource(R.drawable.background_transparent);

                break;
            case id.VAS_2:
                bdsp.stimmungSpeichern("neutral");
                bV3.setBackgroundResource(R.drawable.frame_selected);
                bV1.setBackgroundResource(R.drawable.background_transparent);
                bV2.setBackgroundResource(R.drawable.background_transparent);
                bV4.setBackgroundResource(R.drawable.background_transparent);
                bV5.setBackgroundResource(R.drawable.background_transparent);
                break;
            case id.VAS_3:
                bdsp.stimmungSpeichern("schlecht");
                bV4.setBackgroundResource(R.drawable.frame_selected);
                bV5.setBackgroundResource(R.drawable.background_transparent);
                bV1.setBackgroundResource(R.drawable.background_transparent);
                bV2.setBackgroundResource(R.drawable.background_transparent);
                bV3.setBackgroundResource(R.drawable.background_transparent);
                break;
            case id.VAS_4:
                bdsp.stimmungSpeichern("sehr schlecht");
                bV5.setBackgroundResource(R.drawable.frame_selected);
                bV1.setBackgroundResource(R.drawable.background_transparent);
                bV2.setBackgroundResource(R.drawable.background_transparent);
                bV3.setBackgroundResource(R.drawable.background_transparent);
                bV4.setBackgroundResource(R.drawable.background_transparent);
                break;
        }
    }

    //@SuppressLint("NonConstantResourceId")
    public void onClick(View view){
        switch (view.getId()){
            case id.stuhl_button:
                intentStuhl = new Intent(getActivity(), Eintrag_Stuhl.class);
                getActivity().startActivityForResult(intentStuhl, NEW_STUHL_ACTIVITY_REQUEST_CODE);
                break;
            case id.essen_button:
                intentEssen = new Intent(getActivity(), Eintrag_Essen.class);
                getActivity().startActivityForResult(intentEssen, NEW_ESSEN_ACTIVITY_REQUEST_CODE);
                break;
            case id.einstellungenButton:
                intentEinstellungen = new Intent(getActivity(), Einstellungen.class);
                startActivity(intentEinstellungen);
                break;
        }
    }

    public void setStimmungBackground(){
        String currentStimmung = bdsp.getStimmung();
        switch (currentStimmung){
            case "sehr schlecht" :
                bV5.setBackgroundColor(R.drawable.frame_selected);
                break;
            case "schlecht" :
                bV4.setBackgroundResource(R.drawable.frame_selected);
                break;
            case "neutral" :
                bV3.setBackgroundResource(R.drawable.frame_selected);
                break;
            case "gut" :
                bV2.setBackgroundResource(R.drawable.frame_selected);
                break;
            case "ausgezeichnet" :
                bV1.setBackgroundResource(R.drawable.frame_selected);
                break;
        }
    }
}

