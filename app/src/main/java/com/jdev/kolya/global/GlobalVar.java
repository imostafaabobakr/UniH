package com.jdev.kolya.global;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Java_Dude on 8/26/2018.
 */

public class GlobalVar extends Application {
    public static ArrayList<String> comm;
    public static ArrayList<String> Epower;
    public static ArrayList<String> Mpower;
    public static ArrayList<String> entag;
    public static ArrayList<String> omara;
    public static ArrayList<String> madany;
    public static ArrayList<String> msa7a;
    public static ArrayList<String> temp;

    @Override
    public void onCreate() {
        super.onCreate();
        comm = new ArrayList<>();
        Epower = new ArrayList<>();
        Mpower = new ArrayList<>();
        entag = new ArrayList<>();
        omara = new ArrayList<>();
        madany = new ArrayList<>();
        msa7a = new ArrayList<>();
        temp = new ArrayList<>();

        // put moad
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

    }


}
