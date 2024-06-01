package com.example.flowerstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText tietUsername, tietPassword;
    private Button btnOnLogin;
    private TextView tvSignUp;
    private UserRepository userRepository;

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

        tietUsername = (TextInputEditText) findViewById(R.id.txtUsername);
        tietPassword = (TextInputEditText) findViewById(R.id.txtPassword);
        btnOnLogin = (Button) findViewById(R.id.btnOnLogin);
        tvSignUp = (TextView) findViewById(R.id.btnSignUp);

        userRepository = new UserRepository(this);
        initData();

        ArrayList<User> userList = userRepository.getAllUsers();
        for (User user : userList) {
            Log.d("UserRepository", "User: " + user.getName() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword() + ", Created At: " + user.getCreate_at());
        }

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnOnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = tietUsername.getText().toString().trim();
                String password = tietPassword.getText().toString().trim();
                loginUser(email, password);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            String name = "name" + (i + 1);
            String username = "username" + (i + 1);
            String password = "password" + (i + 1);
            User user = new User(name, username, password);
            userRepository.addUser(user);
        }
    }

    public void loginUser(String strUsername, String strPassword) {
        User user = userRepository.getUser(strUsername);
        if (user != null && user.getPassword().equals(strPassword)) {
            Toast.makeText(LoginActivity.this, "OnLogin success!!!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "OnLogin failed!!!!", Toast.LENGTH_LONG).show();
        }
    }
}