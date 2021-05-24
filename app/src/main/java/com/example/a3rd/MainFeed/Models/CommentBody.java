package com.example.a3rd.MainFeed.Models;

import com.google.gson.annotations.SerializedName;

public class CommentBody {
    @SerializedName("feedId")
    private int feedId;

    @SerializedName("comment")
    private String comment;

    public CommentBody(int feedId, String comment) {
        this.feedId = feedId;
        this.comment = comment;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
