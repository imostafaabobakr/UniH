package com.jdev.kolya.lists;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;


/**
 * Created by Java_Dude on 7/22/2018.
 */

public class postid {
    @Exclude
    public String postid;

    public <T extends postid> T withId(@NonNull final String id) {
        this.postid = id;
        return (T) this;
    }



}
