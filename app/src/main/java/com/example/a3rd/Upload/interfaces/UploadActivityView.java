package com.example.a3rd.Upload.interfaces;

import android.content.Context;

import com.example.a3rd.Upload.models.UploadResponse;

public interface UploadActivityView {
    void UploadSuccess(UploadResponse uploadResponse);

    void UploadFailure(String message);

    void onAttach(Context context);

    void onDetach();
}
