package com.example.a3rd.MainFeed.Models;

import com.google.gson.annotations.SerializedName;

public class FeedLikeResponse {
    @SerializedName("like")
    private String like;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public String getLike() {
        return like;
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
