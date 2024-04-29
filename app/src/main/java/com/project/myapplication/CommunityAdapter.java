package com.project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.myapplication.Communitystr;
import com.project.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.MyViewHolder> {
    Context context;
    ArrayList<Communitystr> Communitylist;
    private DatabaseReference databaseReference;
    private OnClickListener onClickListener;

    // Change the constructor to accept List instead of ArrayList
    public CommunityAdapter(Context context, List<Communitystr> list, OnClickListener onClickListener) {
        this.context = context;
        this.Communitylist = (ArrayList<Communitystr>) list; // Initialize the ArrayList with the provided list
        this.onClickListener = onClickListener;
        // Initialize database reference in the constructor
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("communities");
    }

    public CommunityAdapter(List<Communitystr> communitylist) {
        this.Communitylist = new ArrayList<>(communitylist);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Communitystr communitystr = Communitylist.get(position);
        holder.communitynametv.setText(communitystr.getCommunityName());
        holder.communitydescriptiontv.setText(communitystr.getCommunityDescription());
        Glide.with(holder.itemView.getContext()).load(communitystr.getCommunityImage())
                .into(holder.communityimage);

        // Set click listener
        holder.communitycard.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(position);
            }
        });
        // Set click listener for the card view
        holder.communitycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(holder.itemView.getContext(), "Card clicked: " + communitystr.getCommunityName(), Toast.LENGTH_SHORT).show();
                // Open the new activity here
                //Intent intent = new Intent(context, communityposts.class);
                // Pass any data needed to the new activity using intent extras
               // intent.putExtra("communityID", communitystr.getCommunityID());
                // Start the activity
                //context.startActivity(intent);
            }
        });
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    @Override
    public int getItemCount() {
        return Communitylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView communityimage;
        TextView communitynametv;
        TextView communitydescriptiontv;
        CardView communitycard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            communityimage = itemView.findViewById(R.id.communityimage);
            communitynametv = itemView.findViewById(R.id.communitynametv);
            communitydescriptiontv = itemView.findViewById(R.id.communitydescriptiontv);
            communitycard = itemView.findViewById(R.id.communitycard);
        }
    }
}
