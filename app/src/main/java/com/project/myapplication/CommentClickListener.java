package com.project.myapplication;

import android.content.Context;
import android.content.Intent;

import java.util.List;

public class CommentClickListener implements PostAdapter.OnCommentClickListener{
    private Context mContext;
    public CommentClickListener(Context context) {
        mContext = context;
    }
    @Override
    public void onCommentClick(int position, List<Posts> postList) {
        Intent intent = new Intent(mContext, ShowComment.class);
        intent.putExtra("postID", postList.get(position).getPostID());
        intent.putExtra("postUserUserID", postList.get(position).getPostUserUserID());
        mContext.startActivity(intent);
    }
}
