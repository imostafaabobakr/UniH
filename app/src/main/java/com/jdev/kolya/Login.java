package com.jdev.kolya;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
FirebaseAuth mAuth;
EditText mail,pass;
Button login;
String mailS,passS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth =  FirebaseAuth.getInstance();
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailS = mail.getText().toString();
                passS = pass.getText().toString();
                login.setEnabled(false);
                login.setText("Loggin in");
                if(!mailS.isEmpty()&&!passS.isEmpty()&&mailS!=null&&passS !=null )
                {
                    mAuth.signInWithEmailAndPassword(mailS,passS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(Login.this,MainActivity.class));
                                finish();
                            }else
                            {
                                Toast.makeText(getBaseContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                            login.setEnabled(true);
                            login.setText("Login");
                        }
                    });
                }else
                    Toast.makeText(getBaseContext(),"Please fill all fields",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
