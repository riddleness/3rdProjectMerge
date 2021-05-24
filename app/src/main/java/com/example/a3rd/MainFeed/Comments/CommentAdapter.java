package com.example.a3rd.MainFeed.Comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a3rd.MainFeed.Comments.models.CommentListResponse;
import com.example.a3rd.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private final Context context;
    private ArrayList<CommentListResponse.commentList> commentListResponseArrayList;
    private OnItemClickListener mListener = null ;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView comment_profileImg;
        public TextView comment_profileId,comment_profileContent,comment_likeNum;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);

            comment_profileImg = (ImageView)view.findViewById(R.id.rv_comment_profileImg);
            comment_profileId = (TextView)view.findViewById(R.id.rv_comment_id);
            comment_profileContent = (TextView)view.findViewById(R.id.rv_comment_content);
            comment_likeNum = (TextView)view.findViewById(R.id.rv_comment_likeNum);
        }
    }


    //생성자 - 전달되는 데이터타입에 유의하자.
    public CommentAdapter(ArrayList<CommentListResponse.commentList> commentListResponseArrayList, Context context) {
        this.commentListResponseArrayList = commentListResponseArrayList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder holer = (MyViewHolder) holder;
        //holder.feed_profile_id.setText(feedResultArrayList.get(position).getFeedInfo().getUserId());
        holder.comment_profileId.setText(commentListResponseArrayList.get(position).getUserId());
        holder.comment_likeNum.setText(commentListResponseArrayList.get(position).getLikecount());
        holder.comment_profileContent.setText(commentListResponseArrayList.get(position).getContent());

        Glide.with(context)
                .load(commentListResponseArrayList.get(position).getProfileImgUrl())
                .error(R.drawable.icon_default_profileimg)
                .into(holder.comment_profileImg);



    }

    @Override
    public int getItemCount() {
        return commentListResponseArrayList.size();
    }

    public void remove(int position){
        try {
            commentListResponseArrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void addItem(CommentListResponse.commentList r) {
        commentListResponseArrayList.add(r);
    }



    public interface OnItemClickListener{
        void onItem(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

}