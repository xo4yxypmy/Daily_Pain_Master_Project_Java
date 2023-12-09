package com.fernfog.dailyPain;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fernfog.dailyPain.objects.Symptome;
import com.google.android.material.button.MaterialButton;

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
            cardView.setRadius(32);
            cardView.setElevation(8);

            LinearLayout cardContentLayout = new LinearLayout(view.getContext());

            cardContentLayout.setOrientation(LinearLayout.VERTICAL);

            cardContentLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            cardContentLayout.setOrientation(LinearLayout.VERTICAL);
            cardContentLayout.setGravity(Gravity.CENTER);
            cardContentLayout.setPadding(16, 16, 16, 16);

            TextView textViewName = new TextView(getContext());
            textViewName.setTextSize(40);
            textViewName.setText(symptome.getNameOfCategory());

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.bottomMargin = 40;

            textViewName.setLayoutParams(layoutParams);


            textViewName.setTextColor(Color.parseColor("#" + dbHandler.getCategoryColorByName(symptome.getNameOfCategory())));



            TextView textViewStartTime = new TextView(getContext());
            textViewStartTime.setText(symptome.getStartOfPainDate() + " " + symptome.getStartOfPainTime());

            textViewStartTime.setLayoutParams(layoutParams);

            TextView textViewEndTime = new TextView(getContext());
            textViewEndTime.setText(symptome.getEndOfPainDate() + " " + symptome.getEndOfPainTime());

            textViewEndTime.setLayoutParams(layoutParams);


            TextView textViewLvl = new TextView(getContext());
            String painLvl = String.valueOf(Math.round(symptome.getPainLvl()));
            textViewLvl.setTextSize(25);
            textViewLvl.setText(painLvl);

            if (Math.round(symptome.getPainLvl()) <= 3) {
                textViewLvl.setTextColor(Color.parseColor("#67D65D"));
            } else if (Math.round(symptome.getPainLvl()) >= 4 && Math.round(symptome.getPainLvl()) <= 6) {
                textViewLvl.setTextColor(Color.parseColor("#FFA800"));
            } else if (Math.round(symptome.getPainLvl()) >= 7) {
                textViewLvl.setTextColor(Color.parseColor("#FE0808"));
            }

            textViewLvl.setLayoutParams(layoutParams);

            Typeface customFont = ResourcesCompat.getFont(getContext(), R.font.montserrat);
            textViewName.setTypeface(customFont);
            textViewLvl.setTypeface(customFont);

            cardContentLayout.addView(textViewName);
            cardContentLayout.addView(textViewLvl);
            cardContentLayout.addView(textViewStartTime);
            cardContentLayout.addView(textViewEndTime);

            textViewName.setGravity(Gravity.CENTER);
            textViewLvl.setGravity(Gravity.CENTER);

            MaterialButton button = new MaterialButton(getContext());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = dbHandler.getSymptomId(symptome.getPainLvl(), symptome.getNameOfCategory(), symptome.getStartOfPainDate(), symptome.getEndOfPainDate());

                    dbHandler.deleteSymptom(id);
                }
            });

            button.setText("Видалити");
            button.setIcon(ContextCompat.getDrawable(getContext(),R.drawable.baseline_delete_24));

            button.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            cardContentLayout.addView(button);
            button.setGravity(Gravity.CENTER);

            cardView.addView(cardContentLayout);

            scrollView.addView(cardView);
        }


        return view;
    }
}