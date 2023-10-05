package edu.bluejack22_1.bluejackpharmacy.controller.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.bluejack22_1.bluejackpharmacy.R;
import edu.bluejack22_1.bluejackpharmacy.adapter.MedAdapter;
import edu.bluejack22_1.bluejackpharmacy.controller.About;
import edu.bluejack22_1.bluejackpharmacy.controller.DetailMed;
import edu.bluejack22_1.bluejackpharmacy.model.Medicine;

public class HomeFragment extends Fragment implements MedAdapter.MedClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rvMed;
    MedAdapter medAdapter;
    ImageView imgtoAbout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.i("STATE", "HomeFragment View Created");
//        getMedDB();
        buildRecylclerView();
        imgtoAbout = getView().findViewById(R.id.imgtoAbout);
        imgtoAbout.setOnClickListener(view1 -> startActivity(new Intent(getContext(), About.class)));
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.i("STATE", "HomeFragment Resume");
//        getMedDB();
//        medAdapter.notifyDataSetChanged();
        buildRecylclerView();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onMedClicked(int pos) {
        Intent intent = new Intent(this.getContext(), DetailMed.class);
        intent.putExtra("medicine", Medicine.dbMed.get(pos));
        startActivity(intent);
    }

    private void buildRecylclerView(){
        rvMed = getView().findViewById(R.id.rvMed);
        rvMed.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        medAdapter = new MedAdapter(Medicine.dbMed, this);
        rvMed.setAdapter(medAdapter);
    }
}