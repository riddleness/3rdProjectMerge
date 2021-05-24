package com.example.a3rd.Navigation.Account;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a3rd.MainActivity;
import com.example.a3rd.MainFeed.FragmentMain;
import com.example.a3rd.Navigation.Account.Models.AccountResponse;
import com.example.a3rd.Navigation.Account.UserInfo.FragmentUserInfo;
import com.example.a3rd.Navigation.Account.followModels.FollowRequsetResponse;
import com.example.a3rd.Navigation.Account.followModels.FollowingDeleteResponse;
import com.example.a3rd.Navigation.Account.interfaces.AccountActivityView;
import com.example.a3rd.Navigation.Account.interfaces.followActivityView;
import com.example.a3rd.R;

import java.util.ArrayList;

public class FragmentAccount extends Fragment implements AccountActivityView, followActivityView {



    TextView user_id,user_posts,user_follower,user_following,user_webstie,user_name,user_intro,tv_user_relation_D;
    ImageView user_profileImg,feed,tag;
    Button user_relation_A;
    LinearLayout user_relation_B,user_relation_D,user_relation_B_follow,tag_feed;
    Button user_relation_C;
    View feed_underbar,tag_underbar;
    ImageView img_smallprofile;



//    int userIdx = sSharedPreferences.getInt("userIdx",0);
//    String jwt = sSharedPreferences.getString(X_ACCESS_TOKEN,"null");

    int userIdx = 0;
    String jwt = null;

    private ArrayList<AccountResponse.FeedList> feedImgList;
    private UserFeedAdapter userFeedAdpater;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    int otherUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account,container,false);

        user_id = view.findViewById(R.id.tv_profile_userID);
        user_posts=view.findViewById(R.id.tv_posts);
        user_follower=view.findViewById(R.id.tv_follower);
        user_following=view.findViewById(R.id.tv_following);
        user_profileImg=view.findViewById(R.id.tv_profile_Img);
        user_name=view.findViewById(R.id.tv_profile_userName);
        user_intro=view.findViewById(R.id.tv_profile_intro);
        user_webstie=view.findViewById(R.id.tv_profile_website);
        user_relation_A=view.findViewById(R.id.btn_profile_A);
        user_relation_B=view.findViewById(R.id.ly_profile_B);
        user_relation_B_follow = view.findViewById(R.id.ly_relation_B);
        user_relation_C=view.findViewById(R.id.btn_profile_C);
        user_relation_D=view.findViewById(R.id.ly_profile_D);
        tv_user_relation_D=view.findViewById(R.id.tv_follow_D);
        feed=view.findViewById(R.id.feed_onClick_img);
        tag=view.findViewById(R.id.tag_img);
        feed_underbar=view.findViewById(R.id.feed_underbar);
        tag_underbar=view.findViewById(R.id.tag_feed_underbar);
        tag_feed =view.findViewById(R.id.tag_feed);

        feedImgList = new ArrayList<AccountResponse.FeedList>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_user_feed);
        mLayoutManager = new GridLayoutManager(getContext(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        feedImgList = new ArrayList<>();
        userFeedAdpater = new UserFeedAdapter(feedImgList, getContext());
        mRecyclerView.setAdapter(userFeedAdpater);


        if(getArguments() != null) {
            otherUser = getArguments().getInt("userIdx", 0);
        }
        System.out.println("user인덱스" +otherUser);

        if(otherUser == 0){
            otherUser = userIdx;
        }
        tryGetAccount(otherUser);




            userFeedAdpater.setOnItemClickListener(new UserFeedAdapter.OnItemClickListener() {
            @Override
            public void onItem(View v, int position) {
                System.out.println("유저피드에서 클릭");
                int feedId = feedImgList.get(position).getFeedId();
                System.out.println("feedId는 "+feedId);
                Fragment fragment = new FragmentMain(); // Fragment 생성
                Bundle bundle = new Bundle();
                bundle.putInt("feedId", feedId); // Key, Value
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext visible과 textview 색상변경
                mRecyclerView.setVisibility(view.VISIBLE);
                tag_feed.setVisibility(view.INVISIBLE);
                feed.setImageResource(R.drawable.icon_feed_onclick);
                tag.setImageResource(R.drawable.icon_feed_tag);
                feed_underbar.setBackgroundColor(Color.BLACK);
                tag_underbar.setBackgroundColor(Color.GRAY);
            }
        });

        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.setVisibility(view.INVISIBLE);
                tag_feed.setVisibility(view.VISIBLE);
                feed.setImageResource(R.drawable.icon_feed);
                tag.setImageResource(R.drawable.icon_feed_tag_onclick);
                tag_underbar.setBackgroundColor(Color.BLACK);
                feed_underbar.setBackgroundColor(Color.GRAY);
            }
        });


        return view;
    }

    private void tryGetAccount(int userIdx) {
        final AccountService accountService = new AccountService(this);
        System.out.println("유저 인덱스="+userIdx);
        accountService.AccountGetTest(userIdx);
    }


    //팔로잉 취소
    private void tryCancelFollowing(){
        final FollowService followService = new FollowService(this);
        followService.followingDelete(otherUser);
    }

    //팔로우 신청
    private void tryRequestFollow(){
        final FollowService followService = new FollowService(this);
        followService.followRequest(otherUser);
    }


    @Override
    public void AccountSuccess(AccountResponse accountResponse) {

        System.out.println("jwt= "+jwt);

        String UserId = accountResponse.getAccountResult().getUserInfo().getUserId();
        String ProfileName = accountResponse.getAccountResult().getUserInfo().getUserName();
        String ProfileIntro = accountResponse.getAccountResult().getUserInfo().getProfileIntro();
        String ProfileWebstie = accountResponse.getAccountResult().getUserInfo().getProfileWebSite();

        int FeedNum = accountResponse.getAccountResult().getUserInfo().getFeedNum();
        int FollowerNum = accountResponse.getAccountResult().getUserInfo().getFollowerNum();
        int FollowingNum = accountResponse.getAccountResult().getUserInfo().getFollowingNum();
        String ImgUrl = accountResponse.getAccountResult().getUserInfo().getProfileImgUrl();
        char relation = accountResponse.getAccountResult().getUserInfo().getRelation();

        user_id.setText(UserId);
        user_posts.setText(FeedNum+"");
        user_follower.setText(FollowerNum+"");
        user_following.setText(FollowingNum+"");
        user_name.setText(ProfileName);
        user_intro.setText(ProfileIntro);
        user_webstie.setText(ProfileWebstie);


        System.out.println("프로필 url 값"+accountResponse.getAccountResult().getUserInfo().getProfileImgUrl());

        Glide.with(getActivity().getApplicationContext())
                .load(accountResponse.getAccountResult().getUserInfo().getProfileImgUrl())
                .error(R.drawable.ic_launcher_foreground)
                .into(user_profileImg);


        user_webstie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://"+ProfileWebstie));
                startActivity(i);
            }
        });


        for (com.example.a3rd.Navigation.Account.Models.AccountResponse.FeedList r : accountResponse.getAccountResult().getFeedList())
            userFeedAdpater.addItem(r);
        userFeedAdpater.notifyDataSetChanged();


        switch (relation){
            case 'A':
                user_relation_A.setVisibility(View.VISIBLE);
                user_relation_B.setVisibility(View.INVISIBLE);
                user_relation_C.setVisibility(View.INVISIBLE);
                user_relation_D.setVisibility(View.INVISIBLE);

                user_relation_A.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((com.example.a3rd.MainActivity)getActivity()).replaceFragment(FragmentUserInfo.newInstance());

                    }
                });
                break;

            //팔로잉 상태, 취소가능
            case 'B':
                user_relation_A.setVisibility(View.INVISIBLE);
                user_relation_B.setVisibility(View.VISIBLE);
                user_relation_C.setVisibility(View.INVISIBLE);
                user_relation_D.setVisibility(View.INVISIBLE);
                user_relation_B_follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_relation_B.setVisibility(View.INVISIBLE);
                        user_relation_C.setVisibility(View.VISIBLE);
                        tryCancelFollowing();
                    }
                });
                break;

            //팔로잉 x 상태, 팔로우 신청 가능
            case 'C':
                user_relation_A.setVisibility(View.INVISIBLE);
                user_relation_B.setVisibility(View.INVISIBLE);
                user_relation_C.setVisibility(View.VISIBLE);
                user_relation_D.setVisibility(View.INVISIBLE);

                user_relation_C.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_relation_B.setVisibility(View.VISIBLE);
                        user_relation_C.setVisibility(View.INVISIBLE);
                        tryRequestFollow();
                    }
                });
                break;

            //비공개 계정, 팔로우 신청 가능
            case 'D':
                user_relation_A.setVisibility(View.INVISIBLE);
                user_relation_B.setVisibility(View.INVISIBLE);
                user_relation_C.setVisibility(View.INVISIBLE);
                user_relation_D.setVisibility(View.VISIBLE);

                tv_user_relation_D.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_relation_B.setVisibility(View.VISIBLE);
                        user_relation_D.setVisibility(View.INVISIBLE);
                        tryRequestFollow();
                    }
                });
                break;

        }


    }

    @Override
    public void AccountFailure(String message) {

    }

    @Override
    public void FollowRequestSuccess(FollowRequsetResponse followRequsetResponse) {

    }

    @Override
    public void FollowRequestFailed(String message) {

    }

    @Override
    public void FollowingDeleteSuccess(FollowingDeleteResponse followingDeleteResponse) {

    }

    @Override
    public void FollowingDeleteFailed(String message) {

    }
}
