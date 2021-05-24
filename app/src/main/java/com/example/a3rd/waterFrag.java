package com.example.a3rd;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class waterFrag extends DialogFragment{

    Button btn_yes, btn_no;
    static final String TAG = "waterFrag";
    Handler delayHandler = new Handler();
    int waterdelay = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.water_dialog,container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_no = view.findViewById(R.id.btn_no);
        btn_yes= view.findViewById(R.id.btn_yes);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> input = new HashMap<>();
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("확인","취소버튼입니다.");
                getDialog().dismiss();
                Toast.makeText(getActivity(), "취소되었습니다", Toast.LENGTH_SHORT).show();

            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("확인","물주기버튼입니다");

                input.put("water", "1");
                db.collection("arduinoSensor").document("sensorData")
                        .update(input)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG,"물주기ON");
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "물주기오류...", e);
                            }
                        });
                waterdelay = 1;

                if(waterdelay == 1){  // 물주기 후 5초뒤 끄기

                    delayHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            input.put("water", "0");
                            db.collection("arduinoSensor").document("sensorData")
                                    .update(input)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"물주기OFF");
                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "물주기오류...", e);
                                        }
                                    });
                            waterdelay = 0;

                        }
                    },5000);
                }

                getDialog().dismiss();
                Toast.makeText(getActivity(), "물주기 완료!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
