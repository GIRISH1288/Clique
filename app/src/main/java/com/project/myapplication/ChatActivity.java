package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    AppCompatImageView chatActivityBack;
    TextView chatActivityUserFullName;
    RecyclerView chatActivityRecyclerView;
    FrameLayout chatActivitySend;
    String senderID, receiverID;
    EditText chatActivityInputMessage;
    FirebaseDatabase db;
    String sender_room, receiver_room;
    ArrayList<MessageModelClass> msgArrayList;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Find the RecyclerView
        chatActivityRecyclerView = findViewById(R.id.chatActivityRecyclerView);

        // Initialize the ArrayList for messages
        msgArrayList = new ArrayList<>();

        // Set the layout manager for the RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatActivityRecyclerView.setLayoutManager(linearLayoutManager);

        // Initialize the adapter for the RecyclerView
        messageAdapter = new MessageAdapter(ChatActivity.this, msgArrayList);

        db = FirebaseDatabase.getInstance();
        chatActivityUserFullName = findViewById(R.id.chatActivityUserFullName);
        Intent intent = getIntent();
        chatActivityUserFullName.setText(intent.getStringExtra("name"));
        receiverID = intent.getStringExtra("uid");

        chatActivityInputMessage = findViewById(R.id.chatActivityInputMessage);
        chatActivitySend = findViewById(R.id.chatActivitySend);
        chatActivityBack = findViewById(R.id.chatActivityBack);

        senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sender_room = senderID + receiverID;
        receiver_room = receiverID + senderID;
        DatabaseReference reference = db.getReference().child("users").child(senderID);
        DatabaseReference chatReference = db.getReference().child("users").child(sender_room).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msgArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageModelClass messages = dataSnapshot.getValue(MessageModelClass.class);
                    msgArrayList.add(messages);
                }
                // Notify the adapter after updating msgArrayList
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Handle onDataChange
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });

        // Set the adapter for the RecyclerView
        chatActivityRecyclerView.setAdapter(messageAdapter);

        chatActivitySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatActivityInputMessage.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(ChatActivity.this, "Enter a message", Toast.LENGTH_LONG).show();
                } else {
                    chatActivityInputMessage.setText("");
                    Date date = new Date();
                    MessageModelClass msg = new MessageModelClass(message, senderID, date.getTime());
                    db.getReference().child("chats").child("sender_room").child("messages").push()
                            .setValue(msg)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    db.getReference().child("chats").child("receiver_room").child("messages").push()
                                            .setValue(msg)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    // Handle onComplete
                                                }
                                            });
                                }
                            });
                }
            }
        });
    }

}