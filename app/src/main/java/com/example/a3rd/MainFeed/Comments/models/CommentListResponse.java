package com.example.a3rd.MainFeed.Comments.models;

import com.google.gson.annotations.SerializedName;

public class CommentListResponse {
    @SerializedName("userFeed")
    private userFeed userFeed;

    public CommentListResponse.userFeed getUserFeed() {
        return userFeed;
    }

    public CommentListResponse.commentList[] getCommentList() {
        return commentList;
    }

    public class userFeed{
        @SerializedName("profileImgUrl")
        private String profileImgUrl;

        @SerializedName("userId")
        private String userId;

        @SerializedName("userIdx")
        private int userIdx;

        @SerializedName("caption")
        private String caption;

        public String getProfileImgUrl() {
            return profileImgUrl;
        }

        public String getUserId() {
            return userId;
        }

        public int getUserIdx() {
            return userIdx;
        }

        public String getCaption() {
            return caption;
        }
    }



    @SerializedName("commentList")
    private CommentListResponse.commentList[] commentList;


    public class commentList{
        @SerializedName("profileImgUrl")
        private String profileImgUrl;

        @SerializedName("userId")
        private String userId;

        @SerializedName("commentID")
        private int commentId;

        @SerializedName("userIdx")
        private int userIdx;

        @SerializedName("content")
        private String content;

        @SerializedName("created")
        private String created;

        @SerializedName("likecount")
        private String likecount;

        public String getProfileImgUrl() {
            return profileImgUrl;
        }

        public String getUserId() {
            return userId;
        }

        public int getCommentId() {
            return commentId;
        }

        public int getUserIdx() {
            return userIdx;
        }

        public String getContent() {
            return content;
        }

        public String getCreated() {
            return created;
        }

        public String getLikecount() {
            return likecount;
        }
    }



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
