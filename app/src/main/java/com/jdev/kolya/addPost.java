package com.jdev.kolya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

public class addPost extends AppCompatActivity {

    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        new ChooserDialog().with(this)
                .withStartFile(path)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String s, File file) {
                        Toast.makeText(addPost.this,s,Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .show();

    }
}
