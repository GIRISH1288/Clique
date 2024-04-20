package com.project.myapplication;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

public class CommentClickListener implements PostAdapter.OnCommentClickListener{
    private Context mContext;
    public CommentClickListener(Context context) {
        mContext = context;
    }
    @Override
    public void onCommentClick(int position, List<Posts> postList) {
        Toast.makeText(mContext, "Clicked on Comment", Toast.LENGTH_SHORT).show();
    }
}
