package com.example.myapplication3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;


public class FormActivity1 extends AppCompatActivity {
    private AutoCompleteTextView nameAutoCompleteTextView;
    private EditText nameEditText;
    private CheckBox mCheckbox;
    private RadioGroup loveRadioGroup;
    private RadioButton radioButton;
    private Button continueBtn;
    private TimePicker timePicker;
    private Spinner spinner;
    private ProgressDialog progress;
    String[] arr = {"Nguyen Ngoc Bach", "Le Manh Duy", "Nguyen Nam Hai", "Truong Quoc Bao"};

    ImageButton imgButton;

    public FormActivity1() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.nameAutoCompleteTextView);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        mCheckbox = (CheckBox) findViewById(R.id.mCheckbox);
        continueBtn = (Button) findViewById((R.id.continueBtn));
        loveRadioGroup = (RadioGroup) findViewById(R.id.loveRadioGroup);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, arr);

//        mSpinner = (Spinner) findViewById(R.id.mSpinner);
//
//        mSpinner.setOnItemSelectedListener(FormActivity1.this);
//
//        List<String> categories = new ArrayList<String>();
//        categories.add("Automobile");
//        categories.add("Business Services");
//        categories.add("Computers");
//        categories.add("Education");
//        categories.add("Personal");
//        categories.add("Travel");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        mSpinner.setAdapter(dataAdapter);


        spinner = findViewById(R.id.spinner);
        List<String> categories = Arrays.asList("Category 1", "Category 2", "Category 3");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        ListView listView = findViewById(R.id.listView);
        List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3");
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(listViewAdapter);

        nameAutoCompleteTextView.setThreshold(1);
        nameAutoCompleteTextView.setAdapter(adapter);

        imgButton = (ImageButton) findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You download is resumed", Toast.LENGTH_LONG).show();
            }
        });

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        timePicker.setHour(10);
        timePicker.setMinute(30);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = nameEditText.getText().toString().trim();
                String name2 = nameAutoCompleteTextView.getText().toString().trim();
                Boolean check = false;
                boolean isChecked = mCheckbox.isChecked();

                if (name1.isEmpty() || name2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    check = false;
                } else if (!name1.equals(name2)) {
                    Toast.makeText(getApplicationContext(), "Please enter 2 same names", Toast.LENGTH_SHORT).show();
                    check = false;
                } else if (!isChecked) {
                    Toast.makeText(getApplicationContext(), "Please check the checkbox", Toast.LENGTH_SHORT).show();
                    check = false;
                } else {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    check = true;
                }

                int selected = loveRadioGroup.getCheckedRadioButtonId();
                if (selected != -1) {
                    radioButton = (RadioButton) findViewById(selected);
                    Toast.makeText(FormActivity1.this, "Do you love me?" + radioButton.getText(), Toast.LENGTH_LONG).show();
                    check = true;
                } else {
                    Toast.makeText(FormActivity1.this, "Please select an option", Toast.LENGTH_SHORT).show();
                    check = false;
                }

                if (check) {
                    progress = new ProgressDialog(FormActivity1.this);
                    progress.setMessage("Loading");
                    progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progress.setIndeterminate(false);
                    progress.setProgress(0);
                    progress.setMax(100);
                    progress.show();

                    final int totalProgressTime = 100;
                    final Thread t = new Thread() {
                        @Override
                        public void run() {
                            int jumpTime = 0;

                            while (jumpTime < totalProgressTime) {
                                try {
                                    sleep(200);
                                    jumpTime += 5;
                                    progress.setProgress(jumpTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (jumpTime == totalProgressTime) {
                                progress.dismiss();
                            }
                        }
                    };
                    t.start();
                }
            }
        });


    }

}