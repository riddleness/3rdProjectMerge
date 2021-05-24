package com.example.a3rd.MainFeed.Comments.interfaces;


import com.example.a3rd.MainFeed.Comments.models.CommentResponse;

public interface CommentActivityView {
    void WriteCommentSuccess(CommentResponse commentResponse);

    void WriteCommentFailure(String message);

}
