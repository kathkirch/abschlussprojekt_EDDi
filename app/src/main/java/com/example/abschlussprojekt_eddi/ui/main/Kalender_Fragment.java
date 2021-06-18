package com.example.abschlussprojekt_eddi.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abschlussprojekt_eddi.Entity_Stuhl;
import com.example.abschlussprojekt_eddi.R;
import com.example.abschlussprojekt_eddi.StuhlAdapter;
import com.example.abschlussprojekt_eddi.ViewModel_Stuhl;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Kalender_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Kalender_Fragment extends Fragment {


    StuhlAdapter adapterStuhl;
    Context context = getActivity();
    public ViewModel_Stuhl viewModel_stuhl;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Kalender_Fragment.
     */
    // TODO: Rename and change types and number of parameters

    public static Kalender_Fragment newInstance(String param1, String param2) {
        Kalender_Fragment fragment = new Kalender_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*
    public Kalender_Fragment() {
        // Required empty public constructor
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kalender_, container, false);

        try {
            // create recycler
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);

            // define adapter
            adapterStuhl = new StuhlAdapter();
            System.out.println(adapterStuhl);
            if (adapterStuhl != null){

                System.out.println("hell yeah!");
            }
            recyclerView.setAdapter(adapterStuhl);
        }catch (Exception ex){
            System.out.println(ex + " this is in kalender");
        }

        viewModel_stuhl = new ViewModelProvider(this).get(ViewModel_Stuhl.class);
        try {
            viewModel_stuhl.getAll().observe((LifecycleOwner) context, new Observer<List<Entity_Stuhl>>() {
                @Override
                public void onChanged(List<Entity_Stuhl> entity_stuhls) {
                    //update RecyclerView
                    adapterStuhl.setStuhlNotes(entity_stuhls);
                    Toast.makeText(context, "onChanged", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (NullPointerException npx){
            System.out.println(npx + "kalender fragment");
        }
        return view;
    }

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

     */
}
