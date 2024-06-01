package com.example.flowerstore.Activity;

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

import com.example.flowerstore.Model.User;
import com.example.flowerstore.R;
import com.example.flowerstore.Repository.UserRepository;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText tietName, tietUsername, tietPassword, tietConfirmPassword;
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

        tietName = (TextInputEditText) findViewById(R.id.txtName);
        tietUsername = (TextInputEditText) findViewById(R.id.txtUsername);
        tietPassword = (TextInputEditText) findViewById(R.id.txtPassword);
        tietConfirmPassword = (TextInputEditText) findViewById(R.id.txtConfirmPassword);
        btnOnRegister = (Button) findViewById(R.id.btnOnRegister);
        tvLogin = (TextView) findViewById(R.id.btnLogin);

        userRepository = new UserRepository(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        btnOnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tietName.getText().toString().trim();
                String username = tietUsername.getText().toString().trim();
                String password = tietPassword.getText().toString().trim();
                String confirmPassword = tietConfirmPassword.getText().toString().trim();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "OnRegiter faile!!!!", Toast.LENGTH_LONG).show();
                } else if (password.equals(confirmPassword)) {
                    User user = new User(name, username, password);
                    if(userRepository.addUser(user)){
                        Toast.makeText(RegisterActivity.this, "OnRegiter success!!!!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "OnRegiter faile!!!!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "OnRegiter faile!!!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}