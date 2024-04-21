package com.project.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class portfolioAdapter extends RecyclerView.Adapter<portfolioAdapter.MyViewHolder> {
    Context context;
    ArrayList<portfoliostr> Projectlist;

    public portfolioAdapter(Context context, ArrayList<portfoliostr> list) {
        this.context = context;
        this.Projectlist = list; // Change Projectlist =Projectlist to Projectlist = list
    }

    public portfolioAdapter(List<portfoliostr> projectlist) {
        this.Projectlist = new ArrayList<>(projectlist);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projectcard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        portfoliostr portfoliostrr =Projectlist.get(position);
        holder.tvprojectname.setText(portfoliostrr.getProjectname());
        holder.tvprojectdescription.setText(portfoliostrr.getProjectdescription());

        // Load project image using Glide
        Glide.with(holder.itemView.getContext())
                .load(portfoliostrr.getProjectImage()) // Assuming getProjectImage() returns the image URL
                .into(holder.Ivprojectimage);
    }

    @Override
    public int getItemCount() {
        return Projectlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView Ivprojectimage;
        TextView tvprojectname ;
        TextView tvprojectdescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Ivprojectimage=itemView.findViewById(R.id.Ivprojectimage);
            tvprojectname =itemView.findViewById(R.id.tvprojectname);
            tvprojectdescription = itemView.findViewById(R.id.tvprojectdescription);
        }
    }

}

