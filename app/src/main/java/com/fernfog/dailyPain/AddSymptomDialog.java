package com.fernfog.dailyPain;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddSymptomDialog extends Dialog {

    DatePickerDialog datePickerDialogStart;
    DatePickerDialog datePickerDialogEnd;

    String startOfPain;
    String endOfPain;

    public String category;
    public String color;

    Slider slider;
    Button startOfPainButton, endOfPainButton, createSymptomButton;
    TextView textView;

    public AddSymptomDialog(@NonNull Context context, String category, String color) {
        super(context);

        this.color = color;
        this.category = category;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_symptom);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        textView = findViewById(R.id.textOfCategory);

        textView.setTextColor(Color.parseColor("#" + color));
        textView.setText(category);

        startOfPainButton = findViewById(R.id.setStartDateButton);
        endOfPainButton = findViewById(R.id.setEndDateButton);
        createSymptomButton = findViewById(R.id.createCategoryButton);

        slider = findViewById(R.id.painSlider);

        createSymptomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getContext());

                if (startOfPain != null && endOfPain != null) {
                    dbHandler.addNewSymptom(slider.getValue(), category, startOfPain, endOfPain);
                    Toast.makeText(getContext(), "Симптому додано!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Не заповнено обов'язкове поле", Toast.LENGTH_LONG).show();
                }
            }
        });

        startOfPainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerStart();
            }
        });

        endOfPainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerEnd();
            }
        });
    }

    public void openDatePickerEnd() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endOfPain = dayOfMonth + "/" + month + "/" + year;
            }
        };

        Calendar calendar = Calendar.getInstance();

        datePickerDialogEnd = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialogEnd.show();
    }

    public void openDatePickerStart() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startOfPain = dayOfMonth + "/" + month + "/" + year;
            }
        };

        Calendar calendar = Calendar.getInstance();

        datePickerDialogStart = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialogStart.show();
    }
}
