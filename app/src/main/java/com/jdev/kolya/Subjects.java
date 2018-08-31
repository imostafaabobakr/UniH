package com.jdev.kolya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.jdev.kolya.adapters.SubjectsA;
import com.jdev.kolya.global.GlobalVar;

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

        which = getIntent().getExtras().getInt(CLA);
        comm = GlobalVar.comm;
        Epower = GlobalVar.Epower;
        Mpower = GlobalVar.Mpower;
        entag = GlobalVar.entag;
        omara = GlobalVar.omara;
        msa7a = GlobalVar.msa7a;


        switch (which) {
            case 1:
                temp = comm;
                break;
            case 2:
                temp = Epower;
                break;
            case 3:
                temp = Mpower;
                break;
            case 4:
                temp = entag;
                break;
            case 5:
                temp = omara;
                break;
            case 6:
                temp = madany;
                break;
            case 7:
                temp = msa7a;
                break;
        }
        recyclerView = findViewById(R.id.lista);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        subjectsA = new SubjectsA(temp, which);
        recyclerView.setAdapter(subjectsA);


    }
}
