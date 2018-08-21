package com.jdev.kolya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.jdev.kolya.adapters.SubjectsA;

import java.util.ArrayList;

public class Subjects extends AppCompatActivity {
    //Lists
    ArrayList<String> comm;
    ArrayList<String> Epower;
    ArrayList<String> Mpower;
    ArrayList<String> entag;
    ArrayList<String> omara;
    ArrayList<String> madany;
    ArrayList<String> msa7a;
    ArrayList<String> temp;
    //Adapter
    SubjectsA subjectsA;
    //Recycle View
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    //key
    final static String CLA = "class22";


    int which;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        comm = new ArrayList<>();
        Epower = new ArrayList<>();
        Mpower = new ArrayList<>();
        entag = new ArrayList<>();
        omara = new ArrayList<>();
        madany = new ArrayList<>();
        msa7a = new ArrayList<>();
        temp = new ArrayList<>();
        which = getIntent().getExtras().getInt(CLA);

        comm.add("bls");
        Epower.add("bls");
        Mpower.add("bls");
        comm.add("bls");
        Epower.add("bls");
        Mpower.add("bls");
        comm.add("bls");
        Epower.add("bls");
        Mpower.add("bls");
        comm.add("bls");
        Epower.add("bls");
        Mpower.add("bls");




        switch (which)
        {
            case 1 :
                temp = comm;
                break;
            case 2 :
                temp = Epower;
                break;
            case 3 :
                temp = Mpower;
                break;
            case 4 :
                temp = entag;
                break;
            case 5 :
                temp = omara;
                break;
            case 6 :
                temp = madany;
                break;
            case 7 :
                temp = msa7a;
                break;
        }
        recyclerView = findViewById(R.id.lista);
       gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        subjectsA = new SubjectsA(temp,which);
        recyclerView.setAdapter(subjectsA);


    }
}
