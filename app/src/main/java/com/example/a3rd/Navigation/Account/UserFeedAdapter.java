package com.example.a3rd.Navigation.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a3rd.Navigation.Account.Models.AccountResponse;
import com.example.a3rd.R;

import java.util.ArrayList;

public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapter.ImgViewHolder> {

    private final Context context;
    private ArrayList<AccountResponse.FeedList> feedImgList;
    private OnItemClickListener onItemClickListener = null ;

    public class ImgViewHolder extends RecyclerView.ViewHolder{
        ImageView feed_img;

        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            feed_img = (ImageView) itemView.findViewById(R.id.user_feed_itemImg);
            feed_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        if(onItemClickListener != null){
                            onItemClickListener.onItem(v,pos);
                        }
                    }
                }
            });
        }
    }

    public UserFeedAdapter(ArrayList<AccountResponse.FeedList> feedImgList, Context context){
        this.context = context;
        this.feedImgList = feedImgList;
    }

    @NonNull
    @Override
    public ImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_feed_imgitem,parent,false);
        return new ImgViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImgViewHolder holder, int position) {
        System.out.println("feed img url 값"+feedImgList.get(position).getImgUrl());

        Glide.with(context)
                .load(feedImgList.get(position).getImgUrl())
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.feed_img);
    }

    @Override
    public int getItemCount() {
        return feedImgList.size();
    }

    public void addItem(AccountResponse.FeedList img){
        feedImgList.add(img);
    }

    public interface OnItemClickListener{
        void onItem(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

}
