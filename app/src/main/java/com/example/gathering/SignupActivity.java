package com.example.gathering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailBox, passwordBox, nameBox;
    Button loginBtn, signupBtn;

    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        emailBox = findViewById(R.id.emailBox);
        nameBox = findViewById(R.id.namebox);
        passwordBox = findViewById(R.id.passwordBox);

        loginBtn = findViewById(R.id.loginbtn);
        signupBtn = findViewById(R.id.createBtn);

        signupBtn.setOnClickListener(v -> {
            String email, pass, name;
            email = emailBox.getText().toString();
            pass = passwordBox.getText().toString();
            name = nameBox.getText().toString();

            final User user = new User();
            user.setEmail();
            user.setPass();
            user.setName(name);

            // @Override
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {

                 if(task.isSuccessful()) {
                     database.collection("Users")
                             .document().set(user).addOnSuccessListener(aVoid -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
//                            Toast.makeText(SignupActivity.this, "Account is created.", Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(SignupActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                 }
             });

        });
    }
}