package com.fernfog.dailyPain;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernfog.dailyPain.objects.Symptome;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class AnalisFragment extends Fragment {


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