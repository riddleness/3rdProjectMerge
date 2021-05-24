package com.example.a3rd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class bookAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<bookVO> data;
    LayoutInflater inflater;

    private int[]plantImage={R.drawable.book01, R.drawable.book02,R.drawable.book03,R.drawable.book04,R.drawable.book05,
            R.drawable.book06,R.drawable.book07,R.drawable.book08,R.drawable.book09,R.drawable.book10,R.drawable.book11,R.drawable.book12,
            R.drawable.book13,R.drawable.book14,R.drawable.book15,R.drawable.book16};

    public  bookAdapter(Context context, int layout, List<bookVO> data){
        this.context = context;
        this.layout = layout;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(layout, null);
        }

        TextView tv_plant1 = convertView.findViewById(R.id.tv_plant1);
        ImageView img_plant = convertView.findViewById(R.id.img_btnplant);
        tv_plant1.setText(data.get(position).getPlantName());
//        Glide.with(context).load(data.get(position)).into(img_plant);
        img_plant.setImageResource(plantImage[position]);

        return convertView;
    }

}
