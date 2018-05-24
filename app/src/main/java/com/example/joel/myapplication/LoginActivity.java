package com.example.joel.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoginActivity extends AppCompatActivity implements OnClickListener {
    private String password = "";
    RadioButton rad1;
    RadioButton rad2;
    RadioButton rad3;
    RadioButton rad4;
    RadioButton rad5;
    RadioButton rad6;

    class C04541 extends TypeToken<UserModel> {
        C04541() {
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login);
        this.rad1 = (RadioButton) findViewById(R.id.radioButton);
        this.rad2 = (RadioButton) findViewById(R.id.radioButton2);
        this.rad3 = (RadioButton) findViewById(R.id.radioButton3);
        this.rad4 = (RadioButton) findViewById(R.id.radioButton4);
        this.rad5 = (RadioButton) findViewById(R.id.radioButton5);
        this.rad6 = (RadioButton) findViewById(R.id.radioButton6);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                onClickPassword("1");
                return;
            case R.id.button10:
                onClickPassword("0");
                return;
            case R.id.button2:
                onClickPassword("2");
                return;
            case R.id.button3:
                onClickPassword("3");
                return;
            case R.id.button4:
                onClickPassword("4");
                return;
            case R.id.button5:
                onClickPassword("5");
                return;
            case R.id.button6:
                onClickPassword("6");
                return;
            case R.id.button7:
                onClickPassword("7");
                return;
            case R.id.button8:
                onClickPassword("8");
                return;
            case R.id.button9:
                onClickPassword("9");
                return;
            case R.id.button_delete:
                onClickPassword("Delete");
                return;
            default:
                return;
        }
    }

    private void onClickPassword(String text) {
        switch (this.password.length()) {
            case 0:
                if("Delete".equals(text)){
                    this.rad1.setChecked(false);
                }else{
                    this.rad1.setChecked(true);
                }
                break;
            case 1:
                if("Delete".equals(text)){
                    this.rad1.setChecked(false);
                }else{
                    this.rad2.setChecked(true);
                }
                break;
            case 2:
                if("Delete".equals(text)){
                    this.rad2.setChecked(false);
                }else{
                    this.rad3.setChecked(true);
                }
                break;
            case 3:
                if("Delete".equals(text)){
                    this.rad3.setChecked(false);
                }else{
                    this.rad4.setChecked(true);
                }
                break;
            case 4:
                if("Delete".equals(text)){
                    this.rad4.setChecked(false);
                }else{
                    this.rad5.setChecked(true);
                }
                break;
            case 5:
                if("Delete".equals(text)){
                    this.rad5.setChecked(false);
                }else{
                    this.rad6.setChecked(true);
                }
            case 6:
                if("Delete".equals(text)){
                    this.rad6.setChecked(false);
                }
                break;
        }
        if (this.password.length() <= 6) {
            if("Delete".equals(text) && !"".equals(password)){
                this.password = password.substring(0, password.length() - 1);
                return;
            }
            this.password = this.password.concat(text);
            if (this.password.length() == 6) {
                UserModel userModel = (UserModel) new Gson().fromJson(PreferenceManager.getDefaultSharedPreferences(this).getString(this.password, ""), new C04541().getType());
                if (userModel == null || !this.password.equals(userModel.getPassword())) {
                    Toast.makeText(this, "กรุณากรอกรหัสผ่านให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                    this.rad1.setChecked(false);
                    this.rad2.setChecked(false);
                    this.rad3.setChecked(false);
                    this.rad4.setChecked(false);
                    this.rad5.setChecked(false);
                    this.rad6.setChecked(false);
                    this.password = "";
                    return;
                }
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Constant.INTENT_PASSWORD, this.password);
                startActivity(intent);
                finish();
            }
        }
    }
}
