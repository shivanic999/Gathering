package com.example.gathering;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText emailBox, passwordBox;
    Button loginBtn, signupBtn;

    FirebaseAuth auth;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        auth = FirebaseAuth.getInstance();

        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginbtn);
        signupBtn = findViewById(R.id.createBtn);

        loginBtn.setOnClickListener(v -> {
            dialog.show();
            String email, password;
            email = emailBox.getText().toString();
            password = passwordBox.getText().toString();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this::onComplete);
        });

        signupBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    private void onComplete(Task<AuthResult> task) {
        dialog.dismiss();
        /* startActivity(new Intent(LoginActivity.this, DashboardActivity.class)); */
        if (task.isSuccessful()) {

            startActivity(new Intent(String.valueOf(LoginActivity.this)));
        } else {
            Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}