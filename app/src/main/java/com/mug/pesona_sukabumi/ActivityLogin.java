package com.mug.pesona_sukabumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.mug.pesona_sukabumi.R;

public class ActivityLogin extends AppCompatActivity {

    private EditText userEmail,userPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private Intent MainAct;
    private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.etEmail);
        userPassword = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        pBar = (ProgressBar) findViewById(R.id.pBar);
        mAuth = FirebaseAuth.getInstance();
        MainAct = new Intent (this, com.mug.pesona_sukabumi.MainActivity.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                pBar.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                if (email.isEmpty() || password.isEmpty()){
                    showMessage("Mohon cek data anda");
                    pBar.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
                else
                {
                    signIn(email,password);
                    pBar.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    updateUI();
                }
                else
                {
                    showMessage(task.getException().getMessage());
                    pBar.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void updateUI() {

        startActivity(MainAct);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null ){
            //user is already conect
            updateUI();
        }
    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(), text,Toast.LENGTH_LONG).show();

    }


    public void daftar(View view) {
        Intent intent = new Intent(ActivityLogin.this, ActivityDaftar.class);
        startActivity(intent);
        finish();
    }
}
