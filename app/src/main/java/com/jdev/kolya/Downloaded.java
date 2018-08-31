package com.jdev.kolya;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;

public class Downloaded extends AppCompatActivity {

    ListView list;
    ArrayAdapter<String> adapter;
    String path, name;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded);
        list = findViewById(R.id.listview);
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + getString(R.string.app_name));
        File[] files = dir.listFiles();
        final String[] filename = new String[files.length];
        for (int i = 0; i < filename.length; i++) {
            filename[i] = files[i].getName();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, filename);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                name = filename[i];
                path = Environment.getExternalStorageDirectory() + File.separator + getString(R.string.app_name);


                    uri = Uri.parse("file:///" + path + File.separator + name);
                    String mime = getContentResolver().getType(uri);

                    // Open file with user selected app
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, mime);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);


            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder dialog;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new AlertDialog.Builder(Downloaded.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    dialog = new AlertDialog.Builder(Downloaded.this);
                }

                dialog.setTitle("Delete file")
                        .setMessage("Do you really want to delete this files from your phone ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                name = filename[i+1];
                                path = Environment.getExternalStorageDirectory() + File.separator + getString(R.string.app_name);
                                uri = Uri.parse(  path + File.separator + name);

                                File file = new File(uri.toString());
                                boolean deleted = file.delete();
                                if (deleted)
                                {
                                    Toast.makeText(getBaseContext(), "File Deleted", Toast.LENGTH_LONG).show();
                                    adapter.notifyDataSetChanged();

                                }
                                else
                                    Toast.makeText(getBaseContext(), "Failed to delete file", Toast.LENGTH_LONG).show();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //empty
                            }
                        })
                        .show();


                return true;
            }
        });
    }
}
