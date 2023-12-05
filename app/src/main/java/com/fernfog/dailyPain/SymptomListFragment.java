package com.fernfog.dailyPain;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fernfog.dailyPain.objects.SymptomCategory;
import com.fernfog.dailyPain.objects.Symptome;

import java.util.List;

public class SymptomListFragment extends Fragment {

    LinearLayout scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symptom_list, container, false);

        scrollView = view.findViewById(R.id.symptomCategoriesList);

        DBHandler dbHandler = new DBHandler(getContext());
        List<Symptome> allSymptomes = dbHandler.getAllSymptomes();

        for (Symptome symptome : allSymptomes) {
            CardView cardView = new CardView(view.getContext());
            LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                    800,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardViewParams.setMargins(0, 16, 0, 16);
            cardView.setLayoutParams(cardViewParams);
            cardView.setRadius(8);
            cardView.setElevation(8);

            LinearLayout cardContentLayout = new LinearLayout(view.getContext());

            cardContentLayout.setOrientation(LinearLayout.VERTICAL);

            cardContentLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 250));
            cardContentLayout.setOrientation(LinearLayout.VERTICAL);
            cardContentLayout.setGravity(Gravity.CENTER);
            cardContentLayout.setPadding(16, 16, 16, 16);

            TextView textViewName = new TextView(getContext());
            textViewName.setText(symptome.getNameOfCategory());

            TextView textViewStart = new TextView(getContext());
            textViewStart.setText(symptome.getStartOfPain());


            TextView textViewLvl = new TextView(getContext());
            textViewLvl.setText("" + symptome.getPainLvl());

            cardContentLayout.addView(textViewLvl);
            cardContentLayout.addView(textViewStart);
            cardContentLayout.addView(textViewName);


            cardView.addView(cardContentLayout);

            scrollView.addView(cardView);
        }


        return view;
    }
}