package com.example.a3rd.MainFeed.Models;

import com.google.gson.annotations.SerializedName;

public class HomeResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;


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
