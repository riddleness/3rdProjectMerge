package com.example.a3rd.Navigation.Account.followModels;

import com.google.gson.annotations.SerializedName;

public class followListResponse {
    @SerializedName("result")
    followList[] followLists;


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

    public class followList{

        @SerializedName("userIdx")
        private int userIdx;

        @SerializedName("userId")
        private String userId;

        @SerializedName("profileImgUrl")
        private String profileImgUrl;

        @SerializedName("name")
        private String name;

    }
}
