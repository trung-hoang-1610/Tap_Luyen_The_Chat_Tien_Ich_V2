package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.gson.Gson;


import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.UserDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String PREF_NAME = "login_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_DATA = "user_data";
    public static User mUser;
    TextInputLayout inputEmail, inputPassword;
    private MaterialButton btnLogin, btnLinkToRegister, btnForgotPass;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.edit_email);
        inputPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.button_login);
        btnLinkToRegister = findViewById(R.id.button_register);
        btnForgotPass = findViewById(R.id.button_reset);



        SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isLoggedIn = pref.getBoolean(KEY_IS_LOGGED_IN, false);

        if (isLoggedIn) {
            // Nếu người dùng đã đăng nhập, chuyển hướng đến MainActivity
            mUser = getUserFromSharedPreferences(pref);
            Toast.makeText(LoginActivity.this, "Chào mừng " + mUser.getName()+" trở lại!", Toast.LENGTH_SHORT).show();
            goToMainActivity();

            return; // Không cần tiếp tục xử lý onCreate
        }
        userDAO = new UserDAO();
        userDAO.getAllUsers(new UserDAO.UsersCallback() {
            @Override
            public void onUserListReceived(List<User> users) {


                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isLoggedIn = false;
                        for(User user : users){
                            if(inputEmail.getEditText().getText().toString().trim().equals(user.getEmail()) && inputPassword.getEditText().getText().toString().trim().equals(user.getPassword())){
                                saveLoginState(true);
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                mUser = user;
                                saveUserDataToSharedPreferences(pref,user);
                                goToMainActivity();
                                isLoggedIn = true;
                                break;
                            }
                        }
                        if (!isLoggedIn) {
                            Toast.makeText(LoginActivity.this, "Email hoặc password không hợp lệ!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void saveLoginState(boolean isLoggedIn) {
        SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",mUser);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    private void saveUserDataToSharedPreferences(SharedPreferences pref, User user) {
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String userData = gson.toJson(user);
        editor.putString(KEY_USER_DATA, userData);
        editor.apply();
    }

    private User getUserFromSharedPreferences(SharedPreferences pref) {
        String userData = pref.getString(KEY_USER_DATA, "");
        Gson gson = new Gson();
        return gson.fromJson(userData, User.class);
    }
}