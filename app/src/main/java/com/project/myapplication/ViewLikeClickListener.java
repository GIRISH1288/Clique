package com.project.myapplication;

import android.content.Context;
import android.content.Intent;

import java.util.List;

public class ViewLikeClickListener implements PostAdapter.OnViewLikeClickListener{
    @Override
    public void onViewLikeClick(int position, List<Posts> postList, Context context) {
        Posts post = postList.get(position);
        Intent intent = new Intent(context, ShowLikes.class);
        intent.putExtra("postUserUserID", post.getPostUserUserID());
        intent.putExtra("postID", post.getPostID());
        context.startActivity(intent);
    }
}
