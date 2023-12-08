package com.fernfog.dailyPain;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernfog.dailyPain.objects.Symptome;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AnalisFragment extends Fragment {

    TextView all, l10, l9, l8, l7, l6, l5, l4, l3, l2, l1, l0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_analis, container, false);

        LineChart lineChart = view.findViewById(R.id.lineChart);

        DBHandler dbHandler = new DBHandler(getContext());

        List<Symptome> symptomes = dbHandler.getAllSymptomes();

        all = view.findViewById(R.id.all);
        l10 = view.findViewById(R.id.l10);
        l9 = view.findViewById(R.id.l9);
        l8 = view.findViewById(R.id.l8);
        l7 = view.findViewById(R.id.l7);
        l6 = view.findViewById(R.id.l6);
        l5 = view.findViewById(R.id.l5);
        l4 = view.findViewById(R.id.l4);
        l3 = view.findViewById(R.id.l3);
        l2 = view.findViewById(R.id.l2);
        l1 = view.findViewById(R.id.l1);
        l0 = view.findViewById(R.id.l0);

        all.setText("Кількість симптомів: " + dbHandler.getAllSymptomes().size());

        l0.setText("Кількість 0 рівня: " + dbHandler.getAllSymptomesWithPainLevel(0).size());
        l1.setText("Кількість 1 рівня: " + dbHandler.getAllSymptomesWithPainLevel(1).size());
        l2.setText("Кількість 2 рівня: " + dbHandler.getAllSymptomesWithPainLevel(2).size());
        l3.setText("Кількість 3 рівня: " + dbHandler.getAllSymptomesWithPainLevel(3).size());
        l4.setText("Кількість 4 рівня: " + dbHandler.getAllSymptomesWithPainLevel(4).size());
        l5.setText("Кількість 5 рівня: " + dbHandler.getAllSymptomesWithPainLevel(5).size());
        l6.setText("Кількість 6 рівня: " + dbHandler.getAllSymptomesWithPainLevel(6).size());
        l7.setText("Кількість 7 рівня: " + dbHandler.getAllSymptomesWithPainLevel(7).size());
        l8.setText("Кількість 8 рівня: " + dbHandler.getAllSymptomesWithPainLevel(8).size());
        l9.setText("Кількість 9 рівня: " + dbHandler.getAllSymptomesWithPainLevel(9).size());
        l10.setText("Кількість 10 рівня: " + dbHandler.getAllSymptomesWithPainLevel(10).size());

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < symptomes.size(); i++) {
            Symptome symptome = symptomes.get(i);
            entries.add(new Entry(i, Math.round(symptome.getPainLvl())));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Показник болю");
        dataSet.setValueTextColor(Color.WHITE);
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        Description description = new Description();
        description.setTextColor(Color.WHITE);
        description.setText("Графік симптомів");
        lineChart.setDescription(description);

        lineChart.invalidate();

        return view;
    }
}