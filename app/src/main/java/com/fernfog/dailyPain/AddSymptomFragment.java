package com.fernfog.dailyPain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fernfog.dailyPain.objects.SymptomCategory;

import java.util.List;

public class AddSymptomFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_add_symptom, container, false);

        Button button = view.findViewById(R.id.openDialog);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSymptomDialog addSymptomDialog = new AddSymptomDialog(getContext());

                addSymptomDialog.show();
            }
        });

        DBHandler dbHandler = new DBHandler(getContext());
        List<SymptomCategory> allCategories = dbHandler.getAllCategoriesWithColors();

        for (SymptomCategory category : allCategories) {
            Log.e("CATEGORY", "Name: " + category.getName() + ", Color: " + category.getColor());
        }


        return view;
    }
}