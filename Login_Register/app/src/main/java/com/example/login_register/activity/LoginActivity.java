package com.example.login_register.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.login_register.R;
import com.example.login_register.Repository.UserRepository;
import com.example.login_register.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText tietEmail, tietPassword;
    private Button btnOnLogin;
    private TextView tvSignUp;
    private FirebaseAuth fauth;
    //    private ActivityMainBinding binding;
    private UserRepository userRepository;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tietEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        tietPassword = (TextInputEditText) findViewById(R.id.txtPassword);
        btnOnLogin = (Button) findViewById(R.id.btnOnLogin);
        tvSignUp = (TextView) findViewById(R.id.btnSignUp);

//        fauth = FirebaseAuth.getInstance();

        userRepository = new UserRepository(this);
        initData();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnOnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = tietEmail.getText().toString().trim();
                String password = tietPassword.getText().toString().trim();
                loginUser(email, password);
//                if (TextUtils.isEmpty(email)) {
//                    tietEmail.setError("Email is required");
//                    return;
//                }
//                if (TextUtils.isEmpty(password)) {
//                    tietPassword.setError("Password is required");
//                    return;
//                }
//                if (password.length() < 6) {
//                    tietPassword.setError("Password must be more 6 characters");
//                    return;
//                }

//                fauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Logged in Succesful", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            String username = "user" + (i + 1);
            String password = "password" + (i + 1);
            User user = new User(username, password);
            userRepository.addUser(user);
        }
    }

    public void loginUser(String strUsername, String strPassword) {
        User user = new User(strUsername, strPassword);
        if (null != userRepository && userRepository.checkExistedUser(user)) {
            Toast.makeText(LoginActivity.this, "OnLogin success!!!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "OnLogin failed!!!!", Toast.LENGTH_LONG).show();
        }
    }
}