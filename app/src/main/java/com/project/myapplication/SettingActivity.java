package com.project.myapplication;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // In your Fragment or Activity
        String[] settingOptions = {"Privacy settings", "Notification settings", "Feed preferences", "Account details", "Feedback", "Terms of Service/Privacy Policy"}; // Example setting options

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_setting, settingOptions);
        ListView listViewSettings = findViewById(R.id.settingListViewOptions); // Assuming you have a ListView with id listViewSettings in your layout
        listViewSettings.setAdapter(adapter);

        listViewSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String settingOption = settingOptions[position];

                // Start the corresponding activity based on the clicked option
                switch (settingOption) {
                    case "Privacy settings":
                        Toast.makeText(SettingActivity.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option1Activity.class));
                        break;
                    case "Notification settings":
                        Toast.makeText(SettingActivity.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option2Activity.class));
                        break;
                    case "Feed preferences":
                        Toast.makeText(SettingActivity.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option3Activity.class));
                        break;
                    case "Account details":
                        startActivity(new Intent(SettingActivity.this, SettingsAccountDetails.class));
                        break;
                    case "Feedback":
                        Toast.makeText(SettingActivity.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option3Activity.class));
                        break;
                    case "Terms of Service/Privacy Policy":
                        Toast.makeText(SettingActivity.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option3Activity.class));
                        break;
                    // Add cases for more options as needed
                    default:
                        break;
                }
            }
        });


    }
}