package com.example.a3rd.Upload;

import android.util.Log;

import com.example.a3rd.Upload.interfaces.UploadActivityView;
import com.example.a3rd.Upload.interfaces.UploadInterface;
import com.example.a3rd.Upload.models.UploadBody;
import com.example.a3rd.Upload.models.UploadResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class UploadService {

    private final UploadActivityView muploadActivityView;


    public UploadService(UploadActivityView uploadActivityView) {
        this.muploadActivityView = uploadActivityView;

    }


    void upLoadFeed(UploadBody.imgUrls[] imgUrls, String caption) {
        final UploadInterface uploadInterface = getRetrofit().create(UploadInterface.class);
        uploadInterface.upLoadFeed(new UploadBody(imgUrls,caption)).enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                final UploadResponse uploadResponse = response.body();
                if (uploadResponse == null) {
                    muploadActivityView.UploadFailure(null);
                    Log.d("test","실패");
                    return;
                }

                Log.d("test","성공");
                muploadActivityView.UploadSuccess(uploadResponse);

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                muploadActivityView.UploadFailure(null);
                Log.e("test",t.toString());
            }
        });

    }
}
