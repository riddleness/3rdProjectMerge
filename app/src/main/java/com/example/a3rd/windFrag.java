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
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.HashMap;
import java.util.Map;

public class windFrag extends DialogFragment {

    static final String TAG = "windFrag";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wind_dialog,container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SwitchButton switchButton = (SwitchButton)view.findViewById(R.id.wind_switch);

        //Fragement ->DialogFragment로 값 받기 : 상태값 저장코드로 대체되었습니다
//        Bundle mArgs = getArguments();
//        String mValue = mArgs.getString("switch");
//
//
//        if(mValue.equals("1")){
//            switchButton.setChecked(true);
//
//        }else if(mValue.equals("0")){
//            switchButton.setChecked(false);
//        }

        //저장된 상태값 받아오는 코드입니다.
        SharedPreferences sf = getActivity().getSharedPreferences("sfile",Context.MODE_PRIVATE);
        boolean windcheck = sf.getBoolean("windcheck",false);
        switchButton.setChecked(windcheck);


        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //컬랙션 : arduinoSensor  문서 : input  값 : wind, y
                // 스위치 버튼이 체크되었는지 검사하여 텍스트뷰에 각 경우에 맞게 출력합니다.
                Map<String, Object> input = new HashMap<>();

                if (isChecked){
                    Log.d(TAG,"체크된값" + isChecked);
                    input.put("wind", "1");
                    db.collection("arduinoSensor").document("sensorData")
                            .update(input)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"스위치on");
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }else{

                    Log.d(TAG,"체크된값" + isChecked);
                    input.put("wind", "0");

                    db.collection("arduinoSensor").document("sensorData")
                            .update(input)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"스위치off");
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }

                //상태값 저장하는 코드입니다.
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sfile",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                boolean windcheck = isChecked;
                editor.putBoolean("windcheck",windcheck);
                editor.commit();

            }



        });


        return view;
    }

}
