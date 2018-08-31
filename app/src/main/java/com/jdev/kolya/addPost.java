package com.jdev.kolya;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class addPost extends AppCompatActivity {


    //Layout
    Button done;
    EditText title, desc;
    TextView filepath;
    ImageView upload;
    //Var
    final static String path = String.valueOf(Environment.getExternalStorageDirectory());
    String finalpath;

    //Firebase
    FirebaseFirestore mfire;
    FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    StorageReference riversRef;

    String localtime;
    Uri downloadUrl;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mAuth = FirebaseAuth.getInstance();
        mfire = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        done = findViewById(R.id.donepost);
        title = findViewById(R.id.titleE);
        desc = findViewById(R.id.descE);
        filepath = findViewById(R.id.filepath);
        upload = findViewById(R.id.uploadbtn);
        done.setEnabled(true);
        done.setText("Done");
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChooserDialog().with(addPost.this)
                        .withFilterRegex(false, false, ".*\\.(pdf|doc|docx|txt|zip|ppt|pptx|xlsx|xls|html)")
                        .withStartFile(path)
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String s, File file) {
                                filepath.setText(s);
                                finalpath = s;
                            }
                        })
                        .build()
                        .show();


            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().isEmpty() || finalpath == null) {
                    Snackbar.make(view, "please fill all fields", Snackbar.LENGTH_LONG).show();
                } else {
                    done.setEnabled(false);
                    done.setText("Loading...");
                    localtime = String.valueOf(System.currentTimeMillis());
                    if (finalpath.contains(".pdf"))
                        type = "pdf";

                    else if (finalpath.contains(".txt"))
                        type = "txt";

                    else if (finalpath.contains(".doc") && !finalpath.contains("docx"))
                        type = "doc";

                    else if (finalpath.contains(".docx"))
                        type = "docx";

                    else if (finalpath.contains(".html"))
                        type = "html";

                    else if (finalpath.contains(".zip"))
                        type = "zip";

                    else if (finalpath.contains(".ppt") && !finalpath.contains("pptx"))
                        type = "ppt";

                    else if (finalpath.contains(".pptx"))
                        type = "pptx";

                    else if (finalpath.contains(".xls") && !finalpath.contains("xlsx"))
                        type = "xls";

                    else if (finalpath.contains(".xlsx"))
                        type = "xlsx";


                    riversRef = mStorageRef.child("Posts/" + "kesm/" + "mada/" + "file_" + localtime+"."+type);
                    riversRef.putFile(Uri.parse("file:///" + finalpath)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {
                                downloadUrl = task.getResult().getDownloadUrl();
                                Map<String, Object> map = new HashMap<>();
                                map.put("title", title.getText().toString());
                                map.put("id", mAuth.getCurrentUser().getUid());
                                map.put("url", String.valueOf(downloadUrl));
                                map.put("time", FieldValue.serverTimestamp());
                                map.put("localtime", localtime);
                                map.put("type","."+type);

                                if (!desc.getText().toString().isEmpty())
                                    map.put("desc", desc.getText().toString());

                                mfire.collection("Posts").document("kesm").collection("mada").document(localtime).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            title.setText("");
                                            desc.setText("");
                                            filepath.setText("");
                                            finalpath = "";
                                            Toast.makeText(addPost.this, "Posted", Toast.LENGTH_LONG).show();

                                        } else
                                            Toast.makeText(addPost.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });
                            } else
                                Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            done.setEnabled(true);
                            done.setText("Done");
                        }
                    });

                }
            }
        });

    }
}
