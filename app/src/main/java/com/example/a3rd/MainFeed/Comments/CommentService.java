package com.example.a3rd.MainFeed.Comments;

import android.util.Log;

import com.example.a3rd.MainFeed.Comments.interfaces.CommentActivityView;
import com.example.a3rd.MainFeed.Comments.interfaces.CommentInterface;
import com.example.a3rd.MainFeed.Comments.models.CommentBody;
import com.example.a3rd.MainFeed.Comments.models.CommentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class CommentService {

    private final CommentActivityView mcommentActivityView;


    CommentService(final FragmentComment commentActivityView) {
        this.mcommentActivityView = commentActivityView;
    }



    void WriteComment(int feedId, String comment) {
        final CommentInterface commentInterface = getRetrofit().create(CommentInterface.class);
        commentInterface.WriteComment(new CommentBody(feedId,comment)).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                final CommentResponse commentResponse = response.body();
                if (commentResponse == null) {
                    Log.d("test","실패");
                    mcommentActivityView.WriteCommentFailure(null);
                    return;
                }
                mcommentActivityView.WriteCommentSuccess(commentResponse);
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                mcommentActivityView.WriteCommentFailure(null);
                Log.e("test",t.toString());

            }
        });
    }
}
