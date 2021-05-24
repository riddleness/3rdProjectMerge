package com.example.a3rd.Upload.models;

import com.google.gson.annotations.SerializedName;

public class UploadBody {
    @SerializedName("imgUrls")
    private imgUrls[] imgUrls;

    @SerializedName("caption")
    private String caption;



    public static class imgUrls{
        @SerializedName("url")
        private String url;

        public imgUrls(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public UploadBody.imgUrls[] getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(UploadBody.imgUrls[] imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UploadBody(imgUrls[] imgUrls, String caption) {
        this.imgUrls = imgUrls;
        this.caption = caption;
    }

    


}

