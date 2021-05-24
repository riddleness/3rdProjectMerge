package com.example.a3rd.MainFeed.Comments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a3rd.MainActivity;
import com.example.a3rd.MainFeed.Comments.interfaces.CommentActivityView;
import com.example.a3rd.MainFeed.Comments.interfaces.CommentListActivityView;
import com.example.a3rd.MainFeed.Comments.models.CommentListResponse;
import com.example.a3rd.MainFeed.Comments.models.CommentResponse;
import com.example.a3rd.MainFeed.FragmentMain;
import com.example.a3rd.R;

import java.util.ArrayList;

import static com.example.a3rd.ApplicationClass.smallProfile;

public class FragmentComment extends Fragment implements CommentListActivityView, CommentActivityView {
    ImageView comment_profileImg;
    ImageButton ib_back;
    TextView write_comment,comment_content;
    TextView user_Id, user_Content;
    ImageView img_smallprofile,user_Img;
    MainActivity activity;
    private ArrayList<CommentListResponse.commentList> commentArrayList;
    private CommentAdapter commentAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int feedId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_comment, container, false);
        if(getArguments() != null) {
            feedId = getArguments().getInt("feedId", 0);
        }
        System.out.println("feedId 댓글" +feedId);

        tryGetFeedComments(feedId);
        commentArrayList = new ArrayList<CommentListResponse.commentList>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_comment);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        commentAdapter = new CommentAdapter(commentArrayList, getActivity().getApplicationContext());
        mRecyclerView.setAdapter(commentAdapter);

        user_Id=view.findViewById(R.id.user_id);
        user_Content =view.findViewById(R.id.user_content);
        user_Img=view.findViewById(R.id.user_img);
        ib_back = view.findViewById(R.id.ib_back);

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFrag(new FragmentMain());
            }
        });


        comment_content = view.findViewById(R.id.et_write_comment);
        write_comment = view.findViewById(R.id.comment_ok);

        write_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryWriteComment(feedId,comment_content.getText().toString());
            }
        });


        img_smallprofile = view.findViewById(R.id.comment_profileImg);
        if (smallProfile != null) {
            Glide.with(this)
                    .load(smallProfile)
                    .error(R.drawable.icon_default_profileimg)
                    .into(img_smallprofile);
        }

        return view;
    }

    public static FragmentComment newInstance() {
        return new FragmentComment();
    }

    //write comment
    private void tryWriteComment(int feedId, String comment) {
        System.out.println("WriteComment 함수");
        final CommentService commentService = new CommentService(this);
        commentService.WriteComment(feedId, comment);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private void tryGetFeedComments(int feedId) {
        System.out.println("tryGetFeedComments 함수");
        final CommentListService commentListService = new CommentListService(this);
        commentListService.getFeedComments(feedId);
    }

    @Override
    public void CommentListSuccess(CommentListResponse commentListResponse) {
        System.out.println(commentListResponse.getMessage());
        for (com.example.a3rd.MainFeed.Comments.models.CommentListResponse.commentList r : commentListResponse.getCommentList())
            commentAdapter.addItem(r);
        commentAdapter.notifyDataSetChanged();

        String userId = commentListResponse.getUserFeed().getUserId();
        user_Id.setText(userId);
        String userCaption = commentListResponse.getUserFeed().getCaption();
        user_Content.setText(userCaption);
        String userImg = commentListResponse.getUserFeed().getProfileImgUrl();
        Glide.with(this)
                .load(userImg)
                .error(R.drawable.icon_default_profileimg)
                .into(user_Img);

    }

    @Override
    public void CommentListFailure(String message) {

    }

    @Override
    public void WriteCommentSuccess(CommentResponse commentResponse) {

    }

    @Override
    public void WriteCommentFailure(String message) {

    }
}
