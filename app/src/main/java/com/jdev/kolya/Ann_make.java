package com.jdev.kolya;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Ann_make extends AppCompatActivity {

    FirebaseFirestore mfire;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ann_make);
        mfire = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        final Map<String, Object> map = new HashMap<>();
        map.put("title", "Hady");
        map.put("id", mAuth.getCurrentUser().getUid());
        map.put("desc", "Descccccc");

        mfire.collection("Annou").document("kesm").collection("pre").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    mfire.collection("Notify").document("kesm").collection("pre").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getBaseContext(),"The post added",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
