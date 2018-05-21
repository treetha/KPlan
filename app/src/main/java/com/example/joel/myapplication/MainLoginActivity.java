package com.example.joel.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainLoginActivity extends AppCompatActivity{
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);


    }

    public void OpenLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void OpenRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.main_login_button : OpenLogin(v);
//            case R.id.textView2 : OpenRegister(v);
//        }
//    }
}
