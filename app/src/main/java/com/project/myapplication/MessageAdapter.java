package com.project.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {
    Context context;
    private ArrayList<MessageModelClass> messageModelClassArrayList = new ArrayList<>();

    int ITEM_SENT = 1;
    int ITEM_RECEIVED = 2;

    public MessageAdapter(Context context, ArrayList<MessageModelClass> messageModelClassArrayList) {
        this.context = context;
        this.messageModelClassArrayList = messageModelClassArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_activity_sent_messages, parent, false);
            return new senderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_activity_received_message, parent, false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModelClass msg = messageModelClassArrayList.get(position);
        if (holder.getClass() == senderViewHolder.class) {
            senderViewHolder viewHolder = (senderViewHolder) holder;
            viewHolder.textMessage.setText(msg.getMessage());
            //viewHolder.textDateTime.setText(formatDate(msg.getTimeStamp()));
        } else {
            receiverViewHolder viewHolder = (receiverViewHolder) holder;
            viewHolder.textMessage.setText(msg.getMessage());
            //viewHolder.textDateTime.setText((int) msg.getTimeStamp());
        }
    }

    @Override
    public int getItemCount() {
        if (messageModelClassArrayList != null) {
            return messageModelClassArrayList.size();
        } else {
            return 0; // or any default size you prefer
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageModelClass msg = messageModelClassArrayList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(msg.getSenderID())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVED;
        }
    }

    class senderViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;
        TextView textDateTime;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textSentMessage);
            textDateTime = itemView.findViewById(R.id.textSentDateTime);
        }
    }

    class receiverViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;
        TextView textDateTime;
        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textReceivedMessage);
            textDateTime = itemView.findViewById(R.id.textReceivedDateTime);
        }
    }
}
