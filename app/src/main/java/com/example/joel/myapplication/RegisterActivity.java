package com.example.joel.myapplication;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {
    EditText birthday;
    EditText email;
    EditText id;
    EditText lastname;
    EditText mobile;
    EditText name;
    private OnFocusChangeListener onFocusChangeListener = new C03282();
    private OnClickListener onShowDateCalendar = new C03271();
    EditText password;
    Spinner status;

    class C03271 implements OnClickListener {
        C03271() {
        }

        public void onClick(View v) {
            RegisterActivity.this.showToDatePickerDialog(v);
        }
    }

    class C03282 implements OnFocusChangeListener {
        C03282() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                RegisterActivity.this.showToDatePickerDialog(v);
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment implements OnDateSetListener {
        EditText editText;

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            return new DatePickerDialog(getActivity(), this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            this.editText.setText(day + "/" + month + "/" + year);
        }

        public EditText getEditText() {
            return this.editText;
        }

        public void setEditText(EditText editText) {
            this.editText = editText;
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_register);
        this.name = (EditText) findViewById(R.id.editText);
        this.lastname = (EditText) findViewById(R.id.editText2);
        this.id = (EditText) findViewById(R.id.editText3);
        this.birthday = (EditText) findViewById(R.id.editText4);
        this.mobile = (EditText) findViewById(R.id.editText5);
        this.email = (EditText) findViewById(R.id.editText7);
        this.password = (EditText) findViewById(R.id.editText6);
        this.status = (Spinner) findViewById(R.id.spinner);
        this.birthday.setOnClickListener(this.onShowDateCalendar);
        this.birthday.setOnFocusChangeListener(this.onFocusChangeListener);
    }

    public void onClick(View view) {
        if ("".equals(this.name.getText().toString()) || "".equals(this.password.getText().toString()) || this.password.getText().toString().length() != 6) {
            Toast.makeText(this, "กรุณากรอกชื่อและรหัสผ่าน 6 หลัก", Toast.LENGTH_LONG).show();
            return;
        }
        saveUserInfo();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void saveUserInfo() {
        UserModel userModel = new UserModel(this.name.getText().toString(), this.lastname.getText().toString(), this.id.getText().toString(), this.birthday.getText().toString(), (String) this.status.getSelectedItem(), this.mobile.getText().toString(), this.email.getText().toString(), this.password.getText().toString());
        Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        prefsEditor.putString(userModel.getPassword(), new Gson().toJson(userModel));
        prefsEditor.commit();
    }

    private void showToDatePickerDialog(View v) {
        EditText editText = (EditText) v.findViewById(R.id.editText4);
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setEditText(editText);
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
