package com.example.a3rd.MainFeed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.a3rd.MainFeed.Models.FeedResponse;
import com.example.a3rd.R;

import java.util.ArrayList;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList<FeedResponse.FeedResult> feedResultArrayList;
    private OnItemClickListener mListener = null ;
    public ViewPager vp;
    public String[] FeedImages;
    public int length;
    ImageAdapter imageAdapter;
    public int feedId;



    //생성자 - 전달되는 데이터타입에 유의하자.
    public FeedAdapter(ArrayList<FeedResponse.FeedResult> feedResultArrayList, Context context) {
        this.feedResultArrayList = feedResultArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_feed_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder holer = (MyViewHolder) holder;
        imageAdapter = new ImageAdapter(feedResultArrayList, position,context);
        holder.vp.setAdapter(imageAdapter);

        holder.feed_profile_id.setText(feedResultArrayList.get(position).getFeedInfo().getUserId());
        holder.feed_main_id.setText(feedResultArrayList.get(position).getFeedInfo().getUserId());
        holder.feed_caption.setText(feedResultArrayList.get(position).getFeedInfo().getCaption());
        System.out.println(feedResultArrayList.get(position).getFeedImgUrls().length+"길이");
        length = feedResultArrayList.get(position).getFeedImgUrls().length;

        //댓글
        if(feedResultArrayList.get(position).isCommentWrite() == 1) {
            System.out.println("임"+feedResultArrayList.get(position).isCommentWrite());
            holder.write_comment.setText("댓글 "+(feedResultArrayList.get(position).getFeedInfo().getCommentNum()+1)+"개 "+"모두 보기");
        }
        else {
            holder.feed_commentNum.setText("댓글 " + feedResultArrayList.get(position).getFeedInfo().getCommentNum() + "개 " + "모두 보기");
        }


        //좋아요

        if(feedResultArrayList.get(position).getFeedInfo().getLikeUserId()!=null&&feedResultArrayList.get(position).getFeedInfo().isLiked()==1) {
            holder.iv_feed_like.setImageResource(R.drawable.icon_click_like);
            holder.feed_like.setText(feedResultArrayList.get(position).getFeedInfo().getLikeUserId()+"님 외 "+(feedResultArrayList.get(position).getFeedInfo().getLikeNum()+1)+"명이 좋아합니다");

        }
        else  if(feedResultArrayList.get(position).getFeedInfo().getLikeUserId()!=null&&feedResultArrayList.get(position).getFeedInfo().isLiked()==0) {
            holder.iv_feed_like.setImageResource(R.drawable.icon_heart);
            holder.feed_like.setText(feedResultArrayList.get(position).getFeedInfo().getLikeUserId()+"님 외 "+(feedResultArrayList.get(position).getFeedInfo().getLikeNum())+"명이 좋아합니다");
        }
        else if(feedResultArrayList.get(position).getFeedInfo().getLikeUserId()==null){
            if(feedResultArrayList.get(position).getFeedInfo().isLiked()==1){
                holder.iv_feed_like.setImageResource(R.drawable.icon_click_like);
                holder.feed_like.setText("좋아요 "+(feedResultArrayList.get(position).getFeedInfo().getLikeNum()+1)+"개");
            }
            else{
                holder.iv_feed_like.setImageResource(R.drawable.icon_heart);
                holder.feed_like.setText("좋아요 "+(feedResultArrayList.get(position).getFeedInfo().getLikeNum())+"개");
            }

        }

        Glide.with(context)
                .load(feedResultArrayList.get(position).getFeedInfo().getProfileImgUrl())
                .error(R.drawable.icon_default_profileimg)
                .into(holder.feed_profile_img);



    }

    @Override
    public int getItemCount() {
        return feedResultArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView feed_profile_id, feed_main_id, feed_location, feed_caption, feed_like, feed_commentNum;
        public ImageView feed_profile_img, iv_feed_like, comment_img;
        public EditText comment_content;
        public Button write_comment;
        public ViewPager vp;



        //ViewHolder
        public MyViewHolder(View view) {
            super(view);

            feed_profile_id = (TextView) view.findViewById(R.id.item_profile_id);
            feed_main_id = (TextView) view.findViewById(R.id.item_main_userId);
            feed_location = (TextView) view.findViewById(R.id.item_profile_location);
            feed_caption = (TextView) view.findViewById(R.id.item_main_caption);
            feed_like = (TextView) view.findViewById(R.id.item_main_likeNum);
            vp = (ViewPager)view.findViewById(R.id.vp_main_img);
            feed_profile_img =(ImageView)view.findViewById(R.id.item_profile_img);
            iv_feed_like = (ImageView)view.findViewById(R.id.feed_like);
            feed_commentNum = (TextView)view.findViewById(R.id.item_main_comment);


            iv_feed_like.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItem(v,pos);
                            //좋아요
                            if(feedResultArrayList.get(pos).getFeedInfo().isLiked()==0)
                                feedResultArrayList.get(pos).getFeedInfo().setLike(1);
                                //좋아요 취소
                            else
                                feedResultArrayList.get(pos).getFeedInfo().setLike(0);
                        }
                    }
                }
            });


            feed_profile_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItem(v,pos);
                        }
                    }
                }
            });

            feed_main_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItem(v,pos);
                        }
                    }
                }
            });

            feed_commentNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItem(v,pos);
                        }
                    }
                }
            });

        }
    }




    public void remove(int position){
        try {
            feedResultArrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void addItem(FeedResponse.FeedResult r) {
        feedResultArrayList.add(r);
    }



    public interface OnItemClickListener{
        void onItem(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }


    public class ImageAdapter extends PagerAdapter {
        private final ArrayList<FeedResponse.FeedResult> feedResultArrayList;
        private final int feedId;
        private Context context;

        public ImageAdapter(ArrayList<FeedResponse.FeedResult> feedResultArrayList,int feedId, Context context) {
            this.feedResultArrayList = feedResultArrayList;
            this.context = context;
            this.feedId = feedId;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.viewpager_item,container,false);
            ImageView img = view.findViewById(R.id.viewpager_img);
            Glide.with(context)
                    .load(feedResultArrayList.get(feedId).getFeedImgUrls()[position].getFeedImgUrl())
                    .into(img);

            container.addView(view);
            return view;
        }


        @Override
        public int getCount() {
            return feedResultArrayList.get(feedId).getFeedImgUrls().length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }



        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }

}