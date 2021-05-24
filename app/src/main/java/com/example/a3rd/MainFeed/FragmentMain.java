package com.example.a3rd.MainFeed;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rd.MainActivity;
import com.example.a3rd.MainFeed.Comments.FragmentComment;
import com.example.a3rd.MainFeed.Models.CommentResponse;
import com.example.a3rd.MainFeed.Models.FeedLikeResponse;
import com.example.a3rd.MainFeed.Models.FeedResponse;
import com.example.a3rd.MainFeed.Models.HomeResponse;
import com.example.a3rd.MainFeed.interfaces.CommentActivityView;
import com.example.a3rd.MainFeed.interfaces.FeedItemActivityView;
import com.example.a3rd.MainFeed.interfaces.HomeActivityView;
import com.example.a3rd.Navigation.Account.FragmentAccount;
import com.example.a3rd.Navigation.Account.UserInfo.FragmentUserInfo;
import com.example.a3rd.Navigation.Alarm.FragmentAlarm;
import com.example.a3rd.R;
import com.example.a3rd.Upload.FragmentUpload;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.a3rd.ApplicationClass.smallProfile;


public class FragmentMain extends Fragment implements HomeActivityView, FeedItemActivityView, CommentActivityView {
    TextView feed_profile_id;
    TextView feed_location;
    TextView feed_caption;
    TextView feed_like_num;
    TextView feed_main_id;
    CircleImageView feed_profile_img;
    ImageView feed_img;
    ImageView btn_feed_like;
    ImageButton img_good, img_photo, img_dm;
    MainActivity activity;

    private static final String TAG = "FragmentMain";
    RecyclerView main_feed;


    Context context;


    private FragmentAccount fragmentAccount = new FragmentAccount();


    int userIdx = 0;
    String jwt = null;

    //    int userIdx = sSharedPreferences.getInt("userIdx", 0);
    private ArrayList<FeedResponse.FeedResult> feedResultArrayList;
    private FeedAdapter feedAdpater;
    private RecyclerView.LayoutManager mLayoutManager;
    int feedId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //로그인 되어있는지 -> jwt 토큰 확인
        tryGetTest();
        super.onCreate(savedInstanceState);

    }

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
        View view = inflater.inflate(R.layout.activity_main_home, container, false);


        img_dm = view.findViewById(R.id.img_dm);
        img_good = view.findViewById(R.id.img_good);
        img_photo = view.findViewById(R.id.img_photo);

        //activity_feed
        feed_profile_id = view.findViewById(R.id.item_profile_id);
        feed_main_id = view.findViewById(R.id.item_main_userId);
        feed_location = view.findViewById(R.id.item_profile_location);
        feed_caption = view.findViewById(R.id.item_main_caption);
        feed_like_num = view.findViewById(R.id.item_main_likeNum);
        feed_profile_img = view.findViewById(R.id.item_profile_img);
        feed_img = view.findViewById(R.id.vp_main_img);
        btn_feed_like = view.findViewById(R.id.feed_like);

        feedResultArrayList = new ArrayList<FeedResponse.FeedResult>();
        main_feed = (RecyclerView) view.findViewById(R.id.main_feed);
        mLayoutManager = new LinearLayoutManager(getActivity());
        main_feed.setLayoutManager(mLayoutManager);
        feedAdpater = new FeedAdapter(feedResultArrayList, getActivity().getApplicationContext());
        main_feed.setAdapter(feedAdpater);


        img_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFrag(new FragmentUpload());
            }
        });

        img_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFrag(new FragmentAlarm());
            }
        });
        img_dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFrag(new FragmentUserInfo());
            }
        });



        if (getArguments() != null) {
            feedId = getArguments().getInt("feedId", 0);
        }
        System.out.println("feedId 는?" + feedId);

        //전체피드
        if (feedId == 0) {
            tryGetFeedItem();
        }
        //유저피드
        else {
            tryGetUserFeedItem(feedId);
        }


        feedAdpater.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItem(View v, int position) {
                int feedId = feedResultArrayList.get(position).getFeedInfo().getFeedId();

                switch (v.getId()) {
                    case R.id.feed_like:
                        System.out.println("feed like");
                        feedAdpater.notifyDataSetChanged();
                        tryFeedLike(feedId);
                        break;
                    case R.id.item_profile_id:
                    case R.id.item_main_userId:
                        System.out.println("user Feed");
                        int userIdx = feedResultArrayList.get(position).getFeedInfo().getUserIdx();
                        System.out.println("userIdx는" + userIdx);
                        Fragment fragment = new FragmentAccount(); // Fragment 생성
                        Bundle bundle = new Bundle();
                        bundle.putInt("userIdx", userIdx); // Key, Value
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_frame, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.item_main_comment:
                    case R.id.feed_comments:
                        Fragment fragmentComment = new FragmentComment(); // Fragment 생성
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("feedId", feedId); // Key, Value
                        fragmentComment.setArguments(bundle1);
                        FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.main_frame, fragmentComment);
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();


                    default:
                        break;
                }
            }
        });

        return view;
    }

    //자동로그인 확인
    private void tryGetTest() {
        final HomeService homeService = new HomeService(this);
        homeService.getTest();
    }

    //feed
    private void tryGetFeedItem() {
        System.out.println("tryGetFeedItem 함수");
        final FeedItemService feedItemService = new FeedItemService(this);
        feedItemService.GetFeedList();
    }

    //user feed
    private void tryGetUserFeedItem(int feedId) {
        System.out.println(feedId + "이고" + "tryGetUserFeedItem 함수");
        final FeedItemService feedItemService = new FeedItemService(this);
        feedItemService.GetUserFeedList(feedId);

    }

    //feed like
    private void tryFeedLike(int feedId) {
        System.out.println("FeedLike 함수");
        final FeedItemService feedItemService = new FeedItemService(this);
        feedItemService.PostFeedLike(feedId);
    }

    //write comment
    private void tryWriteComment(int feedId, String comment) {
        System.out.println("WriteComment 함수");
        final CommentService commentService = new CommentService(this);
        commentService.WriteComment(feedId, comment);
    }

    @Override
    public void FeedItemSuccess(FeedResponse feedResponse) {
        System.out.println(feedResponse.getFeedResult());




        for (com.example.a3rd.MainFeed.Models.FeedResponse.FeedResult r : feedResponse.getFeedResult()) {

            if (r.getFeedInfo().getUserIdx() == userIdx) {
                smallProfile = r.getFeedInfo().getProfileImgUrl();
            }
            feedAdpater.addItem(r);

        }

//        Glide.with(this)
//                .load(smallProfile)
//                .error(R.drawable.icon_default_profileimg)
//                .into(img_smallprofile);

        feedAdpater.notifyDataSetChanged();

    }



    @Override
    public void FeedItemFailure(String message) {


    }

    @Override
    public void FeedUserItemSuccess(FeedResponse feedResponse) {
        for (com.example.a3rd.MainFeed.Models.FeedResponse.FeedResult r : feedResponse.getFeedResult())
            feedAdpater.addItem(r);
        feedAdpater.notifyDataSetChanged();

    }

    @Override
    public void FeedUserItemFailure(String message) {

    }

    @Override
    public void FeedLikeSuccess(FeedLikeResponse feedLikeResponse) {
        System.out.println(feedLikeResponse.getLike() + "좋아요");

    }

    @Override
    public void FeedLikeFailure(String message) {

    }

    @Override
    public void HomeSuccess(HomeResponse homeResponse) {
        if (homeResponse.isSuccess()) {
            System.out.println(feedId + "이고" + "HomeSuccess 함수");
            Log.d("test", "자동 로그인");
            feedId = 0;
        } else {
//            Intent intent = new Intent(getActivity().getApplicationContext(), SignInActivity.class);
//            startActivity(intent);
        }

    }

    @Override
    public void HomeFailure(String message) {
//        Intent intent = new Intent(getActivity().getApplicationContext(), SignInActivity.class);
//        startActivity(intent);

    }

    @Override
    public void WriteCommentSuccess(CommentResponse commentResponse) {
        System.out.println("댓글작성 main");
        ((com.example.a3rd.MainActivity) getActivity()).replaceFragment(FragmentComment.newInstance());

    }

    @Override
    public void WriteCommentFailure(String message) {

    }





}
