package com.example.a3rd.MainFeed.interfaces;


import com.example.a3rd.MainFeed.Models.CommentResponse;

public interface CommentActivityView {
    void WriteCommentSuccess(CommentResponse commentResponse);

    void WriteCommentFailure(String message);

}
