package com.example.a3rd.MainFeed.Comments;

import android.util.Log;

import com.example.a3rd.MainFeed.Comments.interfaces.CommentInterface;
import com.example.a3rd.MainFeed.Comments.interfaces.CommentListActivityView;
import com.example.a3rd.MainFeed.Comments.models.CommentListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class CommentListService {
    private final CommentListActivityView mCommentActivityView;

    CommentListService(final CommentListActivityView commentListActivityView) {
        this.mCommentActivityView = commentListActivityView;
    }

    void getFeedComments(int feedId) {
        final CommentInterface commentInterface = getRetrofit().create(CommentInterface.class);
        commentInterface.getFeedComments(feedId, 0,20).enqueue(new Callback<CommentListResponse>() {
            @Override
            public void onResponse(Call<CommentListResponse> call, Response<CommentListResponse> response) {
                final CommentListResponse commentListResponse = response.body();
                if (commentListResponse == null) {
                    mCommentActivityView.CommentListFailure(null);
                    return;
                }

                mCommentActivityView.CommentListSuccess(commentListResponse);
            }

            @Override
            public void onFailure(Call<CommentListResponse> call, Throwable t) {
                mCommentActivityView.CommentListFailure(null);
                Log.e("test",t.toString());

            }

        });
    }

}
