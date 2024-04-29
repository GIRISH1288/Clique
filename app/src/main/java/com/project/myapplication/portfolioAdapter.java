package com.project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class portfolioAdapter extends RecyclerView.Adapter<portfolioAdapter.MyViewHolder> {
    Context context;
    static ArrayList<portfoliostr> Projectlist;
    private static DatabaseReference databaseReference;
    private OnClickListener onClickListener;

    public portfolioAdapter(Context context, ArrayList<portfoliostr> list, OnClickListener onClickListener) {
        this.context = context;
        this.Projectlist = list;
        this.onClickListener = onClickListener;
        // Initialize database reference in the constructor
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("projectdetails");
    }

    public portfolioAdapter(List<portfoliostr> projectlist) {
        this.Projectlist = new ArrayList<>(projectlist);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projectcard, parent, false);
        return new MyViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        portfoliostr portfoliostrr = Projectlist.get(position);
        holder.tvprojectname.setText(portfoliostrr.getProjectname());
        holder.tvprojectdescription.setText(portfoliostrr.getProjectdescription());
        Glide.with(holder.itemView.getContext())
                .load(portfoliostrr.getProjectImage())
                .into(holder.Ivprojectimage);
        holder.projectcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectLink = portfoliostrr.getProjectlink();
                if (projectLink != null && !projectLink.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(projectLink));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    holder.itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Project link not available", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return Projectlist.size();
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Ivprojectimage;
        TextView tvprojectname;
        TextView tvprojectdescription;
        MaterialCardView projectcard;

        public MyViewHolder(@NonNull View itemView, final OnClickListener onClickListener) {
            super(itemView);
            Ivprojectimage = itemView.findViewById(R.id.Ivprojectimage);
            tvprojectname = itemView.findViewById(R.id.tvprojectname);
            tvprojectdescription = itemView.findViewById(R.id.tvprojectdescription);
            projectcard = itemView.findViewById(R.id.projectcard);
        projectcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onClickListener != null) {
                        onClickListener.onClick(position);
                        fetchProjectLink(position);
                    }
                }

                private void fetchProjectLink(final int position) {
                    String projectId = Projectlist.get(position).getProjectId();
                    databaseReference.child(projectId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String projectLink = dataSnapshot.child("projectlink").getValue(String.class);
                            if (projectLink != null && !projectLink.isEmpty()) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(projectLink));
                                itemView.getContext().startActivity(intent);
                            } else {
                                Toast.makeText(itemView.getContext(), "Project link not available", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(itemView.getContext(), "Failed to fetch project link", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        );

        }
    }
}
