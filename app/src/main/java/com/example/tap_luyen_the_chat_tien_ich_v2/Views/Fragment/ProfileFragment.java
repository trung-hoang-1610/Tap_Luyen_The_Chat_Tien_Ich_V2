package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.LoginActivity;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.MainActivity;


public class ProfileFragment extends Fragment {

    Button btnLogOut;
    User user;
    private static final String PREF_NAME = "login_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    TextView txtEmail;
    TextView txtName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtName = view.findViewById(R.id.txtName);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            user = activity.getUser();
        }

        txtName.setText("Xin chào "+user.getName() + "!");

        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(KEY_IS_LOGGED_IN, false);


                editor.apply();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}