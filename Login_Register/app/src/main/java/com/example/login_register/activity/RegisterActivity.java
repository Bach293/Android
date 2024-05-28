package com.example.login_register.activity;

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

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText tietEmail, tietPassword, tietConfirmPassword;
    private TextView tvLogin;
    private Button btnOnRegister;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tietEmail = (TextInputEditText) findViewById(R.id.txtEmail);
        tietPassword = (TextInputEditText) findViewById(R.id.txtPassword);
        tietConfirmPassword = (TextInputEditText) findViewById(R.id.txtConfirmPassword);
        btnOnRegister = (Button) findViewById(R.id.btnOnSignUp);
        tvLogin = (TextView) findViewById(R.id.btnLogin);

        userRepository = new UserRepository(this);
        initData();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        btnOnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tietEmail.getText().toString().trim();
                String password = tietPassword.getText().toString().trim();
                String confirmPassword = tietConfirmPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "OnRegiter faile!!!!", Toast.LENGTH_LONG).show();
                } else if (password.equals(confirmPassword)) {
                    User user = new User(email, password);
                    userRepository.addUser(user);
                    Toast.makeText(RegisterActivity.this, "OnRegiter success!!!!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "OnRegiter faile!!!!", Toast.LENGTH_LONG).show();
                }
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
}