package com.example.a3rd;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sunFrag extends DialogFragment {

    static final String TAG = "sunFrag";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sun_dialog,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final SeekBar sb_led = (SeekBar) view.findViewById(R.id.sb_led);

        //상태 저장값 불러오는 코드입니다
        SharedPreferences sf = getActivity().getSharedPreferences("sfile",Context.MODE_PRIVATE);
        int ledtext = sf.getInt("ledtext",0);
        sb_led.setProgress(ledtext);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> input = new HashMap<>();


        sb_led.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                input.put("led", progress);
                db.collection("arduinoSensor").document("sensorData")
                        .update(input)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG,"led값 조정중" + progress);
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });


                //상태값 저장하는 코드입니다.
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sfile",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int ledtext = progress;
                editor.putInt("ledtext",ledtext);
                editor.commit();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "값출력진행중" + seekBar);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "값출력완료" +seekBar);

            }

        });


        return view;
    }

}
