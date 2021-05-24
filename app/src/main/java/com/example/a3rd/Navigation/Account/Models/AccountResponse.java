package com.example.a3rd.Navigation.Account.Models;

import com.google.gson.annotations.SerializedName;

public class AccountResponse {

    @SerializedName("result")
    private AccountResult accountResult;

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public class AccountResult{
        @SerializedName("userInfo")
        private UserInfo userInfo;

        @SerializedName("feedList")
        private FeedList[] feedList;

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public FeedList[] getFeedList() {
            return feedList;
        }
    }



    public class UserInfo{
        @SerializedName("relation")
        private char relation;

        @SerializedName("profileImgUrl")
        private String profileImgUrl;

        @SerializedName("userId")
        private String userId;

        @SerializedName("userName")
        private String userName;

        @SerializedName("profileIntro")
        private String profileIntro;

        @SerializedName("profileWebSite")
        private String profileWebSite;

        @SerializedName("followingNum")
        private int followingNum;

        @SerializedName("followerNum")
        private int followerNum;

        @SerializedName("feedNum")
        private int feedNum;

        public char getRelation() {
            return relation;
        }

        public String getProfileImgUrl() {
            return profileImgUrl;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public String getProfileIntro() {
            return profileIntro;
        }

        public String getProfileWebSite() {
            return profileWebSite;
        }

        public int getFollowingNum() {
            return followingNum;
        }

        public int getFollowerNum() {
            return followerNum;
        }

        public int getFeedNum() {
            return feedNum;
        }
    }

    public class FeedList {
        @SerializedName("feedId")
        private int feedId;

        @SerializedName("feedImgUrl")
        private String imgUrl;

        @SerializedName("isMultiple")
        private int isMultiple;

        public int getFeedId() {
            return feedId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public int getIsMultiple() {
            return isMultiple;
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

    public AccountResult getAccountResult() {
        return accountResult;
    }
}
