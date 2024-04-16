package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.UserDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private MaterialButton btnRegister, btnLinkToLogin;
    private TextInputLayout inputName, inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = findViewById(R.id.edit_name);
        inputEmail = findViewById(R.id.edit_email);
        inputPassword = findViewById(R.id.edit_password);
        btnRegister = findViewById(R.id.button_register);
        btnLinkToLogin = findViewById(R.id.button_login);

        User user = new User();
        UserDAO userDAO = new UserDAO();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(inputName.getEditText()).getText().toString().trim();
                String email = Objects.requireNonNull(inputEmail.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(inputPassword.getEditText()).getText().toString().trim();
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    user.setEmail(email);
                    user.setPassword(password);

                    if (user.isValidEmail()) {
                        userDAO.setUserAccount(email, password,name);
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(getApplicationContext(), "Register successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Email is not valid!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnLinkToLogin.setOnClickListener(view -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

    }
}