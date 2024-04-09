package com.project.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProfilePortfolioFragment extends Fragment {
   private FloatingActionButton btnaddportfolio1;
   private RecyclerView portfoliorecyler;
   //private PortfolioAdapter portfolioAdapter;
    private String projectname;
    private String projectdescription;
    private String projectimage , projectlink;

   private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ArrayList<portfoliostr> arrportfolio = new ArrayList<>();
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_portfolio, container, false);
        btnaddportfolio1=view.findViewById(R.id.btnaddportfolio1);
        portfoliorecyler=view.findViewById(R.id.portfoliorecyler);
        portfoliostr projectstr =new portfoliostr(projectimage,projectdescription,projectimage,projectlink);
        portfoliorecyler.setLayoutManager(new LinearLayoutManager(requireContext()));
        btnaddportfolio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),portfolioAdd_activity.class);
                startActivity(intent);

            }
        });



        return view;
    }
}

