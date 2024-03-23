package com.project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EditProfileMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_menu);

        String[] settingOptions = {"Profile Picture","Name or Username", "City", "University Details","About Section"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_setting, settingOptions);
        ListView lvEditProfileMenu = findViewById(R.id.lvEditProfileMenu);
        lvEditProfileMenu.setAdapter(adapter);
        lvEditProfileMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String settingOption = settingOptions[position];

                // Start the corresponding activity based on the clicked option
                switch (settingOption) {
                    case "Profile Picture":
                        Toast.makeText(EditProfileMenu.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option1Activity.class));
                        break;
                    case "Name or Username":
                        startActivity(new Intent(EditProfileMenu.this, EditNameUserName.class));
                        break;
                    case "City":
                        Toast.makeText(EditProfileMenu.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option3Activity.class));
                        break;
                    case "University Details":
                        Toast.makeText(EditProfileMenu.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(SettingActivity.this, SettingsAccountDetails.class));
                        break;
                    case "About Section":
                        Toast.makeText(EditProfileMenu.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option3Activity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}