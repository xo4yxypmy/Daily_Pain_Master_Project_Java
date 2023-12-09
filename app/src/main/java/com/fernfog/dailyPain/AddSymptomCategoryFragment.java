package com.fernfog.dailyPain;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.fernfog.dailyPain.objects.SymptomCategory;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class AddSymptomCategoryFragment extends Fragment {

    LinearLayout scrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_symptom, container, false);

        Button button = view.findViewById(R.id.openDialog);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSymptomCategoryDialog addSymptomCategoryDialog = new AddSymptomCategoryDialog(getContext());
                addSymptomCategoryDialog.show();
            }
        });

        DBHandler dbHandler = new DBHandler(getContext());
        List<SymptomCategory> allCategories = dbHandler.getAllCategoriesWithColors();

        scrollView = view.findViewById(R.id.symptomCategories);

        for (SymptomCategory category : allCategories) {
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
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            cardContentLayout.setOrientation(LinearLayout.VERTICAL);
            cardContentLayout.setGravity(Gravity.CENTER);
            cardContentLayout.setPadding(16, 16, 16, 16);

            Button buttonOpenAdd = new Button(view.getContext());
            buttonOpenAdd.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            buttonOpenAdd.setText(category.getName());
            buttonOpenAdd.setTextColor(Color.parseColor("#" + category.getColor()));

            Typeface customFont = ResourcesCompat.getFont(getContext(), R.font.montserrat);

            buttonOpenAdd.setTypeface(customFont);

            buttonOpenAdd.setGravity(Gravity.CENTER);

            buttonOpenAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddSymptomDialog addSymptomDialog = new AddSymptomDialog(getContext(), category.getName(), category.getColor());

                    addSymptomDialog.show();
                }
            });

            MaterialButton deleteCategory = new MaterialButton(getContext());
            deleteCategory.setTypeface(customFont);
            deleteCategory.setText("Видалити категорію");
            deleteCategory.setGravity(Gravity.CENTER);

            deleteCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHandler.deleteCategory(category.getName());
                }
            });

            cardContentLayout.addView(buttonOpenAdd);
            cardContentLayout.addView(deleteCategory);

            cardView.addView(cardContentLayout);

            scrollView.addView(cardView);
        }

        return view;
    }
}
