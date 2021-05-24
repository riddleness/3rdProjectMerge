package com.example.a3rd.Navigation.Account.UserInfo.Models;

import com.google.gson.annotations.SerializedName;

public class EditInfoBody {
    @SerializedName("profileImgUrl")
    private String profileImgUrl;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userId")
    private String userId;

    @SerializedName("profileIntro")
    private String profileIntro;

    @SerializedName("profileWebSite")
    private String profileWebsite;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNum")
    private String phoneNum;

    @SerializedName("gender")
    private char gender;



    public EditInfoBody(String profileImgUrl, String userName, String userId, String profileIntro, String profileWebsite) {
        this.profileImgUrl = profileImgUrl;
        this.userName = userName;
        this.userId = userId;
        this.profileIntro = profileIntro;
        this.profileWebsite = profileWebsite;
        this.gender = 'W';
    }

}
