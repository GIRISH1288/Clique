package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsAccountDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account_details);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        String[] settingOptions = {"Reset password", "Log out"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_setting, settingOptions);
        ListView accountDetailsListView = findViewById(R.id.accountDetailsListView); // Assuming you have a ListView with id listViewSettings in your layout
        accountDetailsListView.setAdapter(adapter);

        accountDetailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String settingOption = settingOptions[position];

                // Start the corresponding activity based on the clicked option
                switch (settingOption) {
                    case "Reset password":
                        Toast.makeText(SettingsAccountDetails.this, "Clicked on " + settingOption, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(requireContext(), Option1Activity.class));
                        break;
                    case "Log out":
                        DocumentReference docRef = db.collection("users").document(userID);
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("notification_token", FieldValue.delete());
                        docRef.update(updates)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                });
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}