package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class CreateProfile extends AppCompatActivity {
    private EditText etCreateProfileBirthDate;
    private Calendar calendar;
    private String birthdate;
    private String city;
    private AutoCompleteTextView tvCreateProfileCity;
    private Spinner spinnerCreateProfileGender;
    private String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        etCreateProfileBirthDate = findViewById(R.id.etCreateProfileBirthDate);
        tvCreateProfileCity = findViewById(R.id.tvCreateProfileCity);
        calendar = Calendar.getInstance();
        spinnerCreateProfileGender = findViewById(R.id.spinnerCreateProfileGender);


        //Initialising Arrays
        String[] genderOptions = {"Male", "Female", "Other"};
        String[] maharashtraCities = {
                "Mumbai", "Pune", "Nagpur", "Nashik", "Aurangabad", "Solapur", "Amravati", "Navi Mumbai",
                "Kolhapur", "Sangli-Miraj & Kupwad", "Malegaon", "Jalgaon", "Akola", "Latur", "Dhule",
                "Ahmednagar", "Chandrapur", "Parbhani", "Ichalkaranji", "Jalna", "Bhusawal", "Nanded",
                "Satara", "Beed", "Wardha", "Yavatmal", "Achalpur", "Osmanabad", "Nandurbar", "Washim",
                "Hinganghat", "Pimpri-Chinchwad", "Gondia", "Baramati", "Panvel", "Ulhasnagar", "Mira-Bhayandar",
                "Nandurbar", "Buldana", "Wani", "Ratnagiri", "Sindhudurg", "Udgir", "Ambajogai", "Gondia",
                "Tumsar", "Ausa", "Ballarpur", "Barshi", "Basmat", "Bhadravati", "Chalisgaon", "Chandrapur",
                "Devgarh", "Deulgaon Raja", "Dharampuri", "Digras", "Gadchiroli", "Gangakhed", "Georai",
                "Ghatanji", "Hinganghat", "Hingoli", "Ichalkaranji", "Junnar", "Kalyan-Dombivli", "Karad",
                "Khamgaon", "Khopoli", "Koradi", "Loha", "Lonavla", "Mahad", "Mahuli", "Malad", "Manchar",
                "Mangalvedhe", "Manjlegaon", "Manmad", "Mehkar", "Mhaswad", "Mira-Bhayandar", "Morshi",
                "Mukhed", "Mul", "Greater Mumbai", "Pandharkaoda", "Paithan", "Palghar", "Palus", "Patur",
                "Pauni", "Pen", "Phaltan", "Pulgaon", "Pune", "Purna", "Pusad", "Rahuri", "Rajura",
                "Ramtek", "Ratnagiri", "Raver", "Risod", "Sailu", "Sangamner", "Sangli", "Sangole",
                "Sasvad", "Satana", "Savner", "Sawantwadi", "Shahade", "Shegaon", "Shendurjana", "Shirdi",
                "Shirpur-Warwade", "Shirur", "Shrigonda", "Shrirampur", "Sillod", "Sinnar", "Solapur",
                "Soyagaon", "Talegaon Dabhade", "Talode", "Tasgaon", "Thane", "Tirora", "Tuljapur", "Tumsar",
                "Uran", "Umarkhed", "Umarga", "Umarkhed", "Vadgaon Kasba", "Vaijapur", "Vasai-Virar",
                "Vita", "Wadgaon Road", "Wai", "Wani", "Wardha", "Warora", "Warud", "Washim", "Wani",
                "Yawal", "Yevla", "Yeola"
        };
        //Initialising and setting adapters
        //Gender
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCreateProfileGender.setAdapter(adapterGender);
        spinnerCreateProfileGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Capture the selected gender
                gender = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if nothing is selected
            }
        });
        //cities
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, maharashtraCities);
        tvCreateProfileCity.setAdapter(adapterCity);
        tvCreateProfileCity.setOnItemClickListener((parent, view, position, id) -> {
            city = (String) parent.getItemAtPosition(position);
        });

    }
    public void showDatePickerDialog(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        updateDateEditText();
                        birthdate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void updateDateEditText() {
        String dateFormat = "dd/MM/yyyy"; // Customize date format if needed
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat, java.util.Locale.getDefault());
        etCreateProfileBirthDate.setText(sdf.format(calendar.getTime()));
    }
}