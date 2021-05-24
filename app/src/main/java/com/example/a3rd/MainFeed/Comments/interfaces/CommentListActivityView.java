package com.example.a3rd.MainFeed.Comments.interfaces;


import com.example.a3rd.MainFeed.Comments.models.CommentListResponse;

public interface CommentListActivityView {

    void CommentListSuccess(CommentListResponse commentListResponse);

    void CommentListFailure(String message);

}
