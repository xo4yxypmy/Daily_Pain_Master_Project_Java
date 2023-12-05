package com.fernfog.dailyPain;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.skydoves.colorpickerview.ColorPickerView;

public class AddSymptomCategoryDialog extends Dialog {

    ColorPickerView colorPickerView;
    Button button;
    TextInputLayout textInputLayout;
    public AddSymptomCategoryDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_symptom_category);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        colorPickerView = findViewById(R.id.colorPickerView);
        button = findViewById(R.id.submitCategoryButton);
        textInputLayout = findViewById(R.id.textInputLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getContext());

                String nameOfCategory = textInputLayout.getEditText().getText().toString();

                if (!nameOfCategory.isEmpty()) {
                    dbHandler.addNewSymptomeCategory(nameOfCategory.trim(), colorPickerView.getColorEnvelope().getHexCode());
                    Toast.makeText(getContext(), "Категорію створено", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Не заповнено обов'язкове поле", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
