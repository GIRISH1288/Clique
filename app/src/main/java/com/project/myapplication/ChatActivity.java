package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    AppCompatImageView chatActivityBack;
    TextView chatActivityUserFullName;
    RecyclerView chatActivityRecyclerView;
    FrameLayout chatActivitySend;
    String senderID, receiverID;
    EditText chatActivityInputMessage;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatActivityBack = findViewById(R.id.chatActivityBack);
        chatActivityUserFullName = findViewById(R.id.chatActivityUserFullName);
        chatActivityRecyclerView = findViewById(R.id.chatActivityRecyclerView);
        chatActivitySend = findViewById(R.id.chatActivitySend);
        chatActivityInputMessage = findViewById(R.id.chatActivityInputMessage);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        chatActivityUserFullName.setText(intent.getStringExtra("name"));
        receiverID = intent.getStringExtra("uid");
        senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("users").document(senderID);
        


        chatActivitySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatActivityInputMessage.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(ChatActivity.this, "Enter a message", Toast.LENGTH_LONG).show();
                }
                chatActivityInputMessage.setText("");
                Date date = new Date();
                MessageModelClass messagess = new MessageModelClass(message, senderID, date.getTime());
            }
        });
    }
}