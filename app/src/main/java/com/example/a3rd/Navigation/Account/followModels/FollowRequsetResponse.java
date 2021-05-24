package com.example.a3rd.Navigation.Account.followModels;

import com.google.gson.annotations.SerializedName;

public class FollowRequsetResponse {

    @SerializedName("follow")
    private String follow;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public String getFollow() {
        return follow;
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
