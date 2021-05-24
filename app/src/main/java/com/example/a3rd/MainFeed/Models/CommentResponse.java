package com.example.a3rd.MainFeed.Models;

import com.google.gson.annotations.SerializedName;

public class CommentResponse {
    @SerializedName("userId")
    private String userId;

    @SerializedName("commentId")
    private int commentId;

    @SerializedName("profileImgUrl")
    private String profileImgUrl;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public String getUserId() {
        return userId;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public int getCommentId() {
        return commentId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
