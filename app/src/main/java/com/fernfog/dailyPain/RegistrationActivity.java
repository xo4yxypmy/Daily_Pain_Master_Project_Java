package com.fernfog.dailyPain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    Button button;

    String birth;

    Button submitButton;

    TextInputLayout firstName;
    TextInputLayout lastName;
    TextInputLayout patronymic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        button = findViewById(R.id.choiseOfDate);

        submitButton = findViewById(R.id.account_reg);

        firstName = findViewById(R.id.textInputLayout);
        lastName = findViewById(R.id.textInputLayout2);
        patronymic = findViewById(R.id.textInputLayout3);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameT = firstName.getEditText().getText().toString().trim();
                String lastNameT = lastName.getEditText().getText().toString().trim();
                String patronymicT = patronymic.getEditText().getText().toString().trim();


                if (!firstNameT.isEmpty() && !lastNameT.isEmpty() && !patronymicT.isEmpty() && birth != null) {
                    DBUsersHandler dbUsersHandler = new DBUsersHandler(RegistrationActivity.this);

                    dbUsersHandler.addNewUser(firstNameT, lastNameT, patronymicT, birth);
                    Toast.makeText(RegistrationActivity.this, "Обліковий запис створено", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);

                    startActivity(intent);

                } else {
                    Toast.makeText(RegistrationActivity.this, "Не заповнено обов'язкове поле", Toast.LENGTH_LONG).show();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

    }

    public void openDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birth = dayOfMonth + " " + month + " " + year;
            }
        };

        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}