package com.project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CommentClickListener implements PostAdapter.OnCommentClickListener{
    private Context mContext;
    public CommentClickListener(Context context) {
        mContext = context;
    }

    @Override
    public void onCommentClick(String postID, String postUserUserID) {
        Intent intent = new Intent(mContext, ShowComment.class);
        intent.putExtra("postID", postID);
        Toast.makeText(mContext,postID, Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext,postUserUserID, Toast.LENGTH_SHORT).show();
        intent.putExtra("postUserUserID", postUserUserID);
        mContext.startActivity(intent);
    }

    @Override
    public void onCommentClick() {

    }

}
