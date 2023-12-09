package com.fernfog.dailyPain;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fernfog.dailyPain.objects.SymptomCategory;
import com.fernfog.dailyPain.objects.Symptome;
import com.fernfog.dailyPain.objects.User;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportFragment extends Fragment {

    Button button;
    Context context;
    static DBHandler dbHandler;
    static List<SymptomCategory> symptomes;
    String categorySelected;

    Button openFileDirectory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        button = view.findViewById(R.id.testBSLKFJM);
        Spinner spinner = view.findViewById(R.id.spinner);

        context = getContext();

        dbHandler = new DBHandler(context);

        symptomes = dbHandler.getAllCategoriesWithColors();

        List<String> symptomesNames = new ArrayList<>();
        openFileDirectory = view.findViewById(R.id.openDirectoryButton);

        for (SymptomCategory symptome : symptomes) {
            symptomesNames.add(symptome.getName());
        }

        for (String categoryName : symptomesNames) {
            Log.d("CategoryName", categoryName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                symptomesNames
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        openFileDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File myPdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS), "MyPDFs");
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                Uri uri = Uri.fromFile(myPdfFolder);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                generatePdf(categorySelected, view.getContext());
            }
        });

        return view;
    }

    public static void generatePdf(String category, Context context) {
        User user = dbHandler.getUser();

        File pdfDirectory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "MyPDFs");


        if (!pdfDirectory.exists()) {
            pdfDirectory.mkdirs();
        }

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        String filePath = new File(pdfDirectory, "звіт-" + formattedDate + ".pdf").getAbsolutePath();

        try {

            String FONT = "/assets/fonts/Montserrat-Regular.ttf";

            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.IDENTITY_H);

            PdfWriter writer = new PdfWriter(filePath);

            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf);

            document.setFont(font);

            List<Symptome> symptomeList = dbHandler.getAllSymptomesInCategory(category);

            document.add(new Paragraph("Звіт спостереження").setTextAlignment(TextAlignment.CENTER).setUnderline());
            document.add(new Paragraph("ПІБ: " + user.getFirstName() + " " + user.getLastName() + " " + user.getPatronymic()));
            document.add(new Paragraph("Дата народження: " + user.getBirthday()));
            document.add(new Paragraph("Категорія спостереження: " + category));
            document.add(new Paragraph("Загальна кількість симптомів: " + symptomeList.size()));

            document.add(new Paragraph("Кількість симптомів за рівнем болю: "));

            Table table_ = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));



            Cell headerCell1_ = new Cell().add(new Paragraph("0"));
            Cell headerCell2_ = new Cell().add(new Paragraph("1"));
            Cell headerCell3_ = new Cell().add(new Paragraph("2"));
            Cell headerCell4_ = new Cell().add(new Paragraph("3"));
            Cell headerCell5_ = new Cell().add(new Paragraph("4"));
            Cell headerCell6_ = new Cell().add(new Paragraph("5"));
            Cell headerCell7_ = new Cell().add(new Paragraph("6"));
            Cell headerCell8_ = new Cell().add(new Paragraph("7"));
            Cell headerCell9_ = new Cell().add(new Paragraph("8"));
            Cell headerCell10_ = new Cell().add(new Paragraph("9"));
            Cell headerCell11_ = new Cell().add(new Paragraph("10"));



            table_.addCell(headerCell1_);
            table_.addCell(headerCell2_);
            table_.addCell(headerCell3_);
            table_.addCell(headerCell4_);
            table_.addCell(headerCell5_);
            table_.addCell(headerCell6_);
            table_.addCell(headerCell7_);
            table_.addCell(headerCell8_);
            table_.addCell(headerCell9_);
            table_.addCell(headerCell10_);
            table_.addCell(headerCell11_);

            for (float i = 0; i < 11; i++) {
                Cell cell1 = new Cell().add(new Paragraph(String.valueOf(dbHandler.getAllSymptomesWithPainLevel(i).size())));
                table_.addCell(cell1);
            }

            document.add(table_);

            document.add(new Paragraph("Перелік введених користувачем симптомів: "));

            Table table = new Table(5);
            Cell headerCell1 = new Cell().add(new Paragraph("№"));
            Cell headerCell2 = new Cell().add(new Paragraph("Початок"));
            Cell headerCell3 = new Cell().add(new Paragraph("Кінець"));
            Cell headerCell4 = new Cell().add(new Paragraph("Рівень болю"));
            Cell headerCell5 = new Cell().add(new Paragraph("Додатково"));

            table.addCell(headerCell1);
            table.addCell(headerCell2);
            table.addCell(headerCell3);
            table.addCell(headerCell4);
            table.addCell(headerCell5);

            for (int i = 0; i < symptomeList.size(); i++) {
                Symptome symptome = symptomeList.get(i);

                Cell cell1 = new Cell().add(new Paragraph(String.valueOf(i + 1)));
                Cell cell2 = new Cell().add(new Paragraph(symptome.getStartOfPainTime() + " " + symptome.getStartOfPainDate()));
                Cell cell3 = new Cell().add(new Paragraph(symptome.getEndOfPainTime() + " " + symptome.getEndOfPainDate()));
                Cell cell4 = new Cell().add(new Paragraph(String.valueOf(Math.round(symptome.getPainLvl()))));
                Cell cell5 = new Cell().add(new Paragraph(String.valueOf(symptome.getAdditional())));

                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);
            }

            document.add(table);

            document.add(new Paragraph("Звіт сформовано на основі введених користувачем даних."));
            document.add(new Paragraph("Результат звіту спостереження не є достатньою підставою для постановки діагнозу."));
            document.add(new Paragraph("Інтерпретація звіту спостереження для постановки діагнозу виконується тільки лікарем."));
            document.add(new Paragraph("Згенеровано за допомогою Daily Pain."));

            document.close();

            Toast.makeText(context, "Звіт створено", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}