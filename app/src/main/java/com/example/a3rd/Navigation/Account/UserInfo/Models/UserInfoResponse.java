package com.example.a3rd.Navigation.Account.UserInfo.Models;

import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {

    @SerializedName("result")
    private UserInfoResult userInfoResult;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public class UserInfoResult{
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

        public String getProfileImgUrl() {
            return profileImgUrl;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserId() {
            return userId;
        }

        public String getProfileIntro() {
            return profileIntro;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public char getGender() {
            return gender;
        }

        public String getProfileWebSite() {
            return profileWebsite;
        }
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

    public UserInfoResult getAccountResult() {
        return userInfoResult;
    }
}
