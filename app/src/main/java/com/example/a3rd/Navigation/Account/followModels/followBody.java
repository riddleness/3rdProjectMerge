package com.example.a3rd.Navigation.Account.followModels;

import com.google.gson.annotations.SerializedName;

public class followBody {
    @SerializedName("followUserIdx")
    private int followUserIdx;

    public followBody(int followUserIdx) {
        this.followUserIdx = followUserIdx;
    }
}
