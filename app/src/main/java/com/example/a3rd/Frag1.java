package com.example.a3rd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.BlockedNumberContract;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Frag1 extends Fragment {
    private View fragment;
    ImageButton ibtn_water,ibtn_temp,ibtn_wind,ibtn_sun;
    TextView tv_plantname, tv_height, tv_soilhum, tv_temp, tv_hum, tv_wind, tv_sun;
    ImageView img_face;
    int[] imgs = {R.drawable.face_love,R.drawable.face_sad,R.drawable.face_sad_sleepy,
            R.drawable.face_smiling,R.drawable.face_smiling_closed, R.drawable.face_sweating};
    ArrayList<SensorVO>data;
    static final String TAG = "파이어베이스";

    //현재시간 측정
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
    String time = dateFormat.format(date);
    int time2 = Integer.parseInt(time);

//    double soilRs = 0;
//    long tempRs = 0;
//    double humRs = 0;
//    long heightrs = 0;

    long soilRs = 0;
    long tempRs = 0;
    long humRs = 0;
    long heightrs = 0;
    long heightOr = 0;

    //키성장 비교를위한 오리지널 변수
//    long heightOr = 0;
    boolean hcheck = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment=inflater.inflate(R.layout.frag1, container, false);

        ibtn_water = fragment.findViewById(R.id.ibtn_water);
        ibtn_temp = fragment.findViewById(R.id.ibtn_temp);
        ibtn_wind = fragment.findViewById(R.id.ibtn_wind);
        ibtn_sun = fragment.findViewById(R.id.ibtn_sun);
        img_face = fragment.findViewById(R.id.img_face);
        tv_plantname = fragment.findViewById(R.id.tv_plantname);
        tv_height = fragment.findViewById(R.id.tv_height);
        tv_soilhum = fragment.findViewById(R.id.tv_soilhum);
        tv_temp = fragment.findViewById(R.id.tv_temp);
        tv_hum = fragment.findViewById(R.id.tv_hum);
        tv_wind = fragment.findViewById(R.id.tv_wind);
        tv_sun = fragment.findViewById(R.id.tv_sun);


        data = new ArrayList<>();


//        //리얼타임 데이터베이스 파이어베이스 DB 주소
//        String db_url = "https://potstargram-default-rtdb.firebaseio.com/";
//
//        //파이어베이스로 접근 및 테스트 데이터 저장
//        FirebaseDatabase database = FirebaseDatabase.getInstance(db_url);
//        DatabaseReference myRef = database.getReference("Potstar");
//
////        테스트용 DB값 저장
////        유저이름, 식물이름, 상태페이스, 습도, 온도, 높이
//
////        myRef.child("sensor").setValue(new SensorVO("apple","애플민트","1","50","20","10"));
//
//        myRef.child("sensor").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                //VO객체로 받아오기 테스트중...
//                SensorVO vo = snapshot.getValue(SensorVO.class);
//                tv_plantname.setText(vo.getUser_plant());
//                tv_water.setText(vo.getSensor_dank()+"%");
//                tv_temp.setText(vo.getSensor_temp()+"°C");
//                tv_height.setText(vo.getSensor_pir() + "cm");
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//
//            }
//        });


        //firestore 데이터베이스 접속하기
        FirebaseFirestore db = FirebaseFirestore.getInstance();


////      테스트 데이터 작성 (key, value)-----------------------
//        Map<String, Object> sensor = new HashMap<>();
//        sensor.put("water", "40");
//        sensor.put("temp", "20");
//        sensor.put("height", "10");
//        sensor.put("plant", "테스트");
//        sensor.put("user", "abc");
//
////         Add a new document with a generated ID
//        db.collection("sensors")
//                .add(sensor)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });


        // Firestore 안드로이드로 데이터 받아오기
        // 한번만 다운받아진다 실시간XX 로그인으로 사용하자
//        db.collection("sensors")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            //task에서 꺼내서 document에 넣는다
//                            //그리고 ID = DATA 순으로 출력한다.
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData() +"frag");
//                                Log.d(TAG,document.get("water").toString() + "test");
//
//                                tv_water.setText(document.get("water")+"%");
//                                tv_plantname.setText(document.get("plant")+"");
//                                tv_height.setText(document.get("height")+"cm");
//                                tv_temp.setText(document.get("temp")+"°C");
//                            }
//
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//
//                    }
//
//                });


        //측정된 센서값 받아오기 Firestore사용 (arduinoSensor,arduinodata)
        final DocumentReference docRef = db.collection("arduinoSensor").document("arduinodata");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());

                    // 각각 센서값 받아서 화면에 출력
                    tv_soilhum.setText(snapshot.get("soilhum")+"%");
                    tv_height.setText(snapshot.get("distance")+"cm");
                    tv_temp.setText(snapshot.get("temp")+"℃");
                    tv_hum.setText(snapshot.get("hum")+"%");

                    // 화분 센서 데이터 변수에 넣어주기
//                    soilRs = (double)(snapshot.get("soilhum"));
//                    tempRs = (long)snapshot.get("temp");
//                    humRs = (double)snapshot.get("hum");
//                    heightrs = (long)snapshot.get("distance");
//                    heightOr = (long)snapshot.get("height");

                    soilRs = snapshot.getLong("soilhum");
                    tempRs = snapshot.getLong("temp");
                    humRs = snapshot.getLong("hum");
                    heightrs = snapshot.getLong("distance");
//                    heightOr = snapshot.getLong("height");


                    //==========성장에 따른 축하문구=========
//                    if(heightOr==0){
//                        heightOr = heightrs;
//                        Map<String, Object> input = new HashMap<>();
//                        input.put("height", heightrs);
//                        db.collection("arduinoSensor").document("arduinodata")
//                                .update(input)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Log.d(TAG,"최초 크기 입력 성공!");
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w(TAG, "최초 크기 입력실패", e);
//                                    }
//                                });
//                    };
//
//                    SharedPreferences sf = getActivity().getSharedPreferences("sfile",Context.MODE_PRIVATE);
//                    hcheck = sf.getBoolean("hcheck",false);
//
//                    if ((heightrs-heightOr)>2 && hcheck==false){
//                        Map<String, Object> input = new HashMap<>();
//                        input.put("img", "축축하");
//                        db.collection("arduinoSensor").document("imgData")
//                                .update(input)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Log.d(TAG,"축하합니다 성공!");
//                                    }
//                                })
//
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.w(TAG, "축하합니다 실패...", e);
//                                    }
//                                });
//
//                        //한번만 축하하는 표정이 나오고 true값 전송
//                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sfile",Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        hcheck = true;
//                        editor.putBoolean("hcheck",hcheck);
//                        editor.commit();
//                    }
                    Map<String, Object> input = new HashMap<>();

                    // =========상황별 얼굴 아이콘 변경 안드로이드 + 얼굴값전송===========
                    if(soilRs<100 && tempRs>27) {  // 토양 습도 높고 온도 높을때
                        img_face.setImageResource(imgs[1]);
                        input.put("img", "슬픔");
                        db.collection("arduinoSensor").document("imgData")
                                .update(input)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"슬픔!");
                                    }
                                });
                    }else if (soilRs<100){  // 토양 습도 높을때
                        img_face.setImageResource(imgs[2]);
                    }else if(tempRs>27){ // 온도 높을때
                        img_face.setImageResource(imgs[5]);
                        input.put("img", "화남");
                        db.collection("arduinoSensor").document("imgData")
                                .update(input)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"화남!");
                                    }
                                });
                    }else if(time2>1 && time2<700){ // 밤시간때
                        img_face.setImageResource(imgs[4]);
                    }else{ //평상시에
                        img_face.setImageResource(imgs[3]);
                        input.put("img", "좋음");
                        db.collection("arduinoSensor").document("imgData")
                                .update(input)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"좋음!");
                                    }
                                });

                    }

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        //입력된 INPUT값 받아와서 저장하기
        final DocumentReference docRef2 = db.collection("arduinoSensor").document("sensorData");
        docRef2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {

                    String wind2 = snapshot.get("wind").toString();
                    String led2 = snapshot.get("led").toString();
                    int led3 = Integer.parseInt(led2);

                    snapshot.get("wind");

                    if(wind2.equals("1")){
                        tv_wind.setText("켜짐");
                    }else{
                        tv_wind.setText("꺼짐");
                    }
                    if(led3>70){
                        tv_sun.setText("밝게");
                    }else if(led3>40){
                        tv_sun.setText("중간");
                    }else if(led3>1){
                        tv_sun.setText("어둡게");
                    }else if(led3==0){
                        tv_sun.setText("꺼짐");
                    }


                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


        ibtn_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                waterFrag dialFrag = new waterFrag();
                dialFrag.show(fm,"waterFrag");

            }
        });

        ibtn_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ibtn_wind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                windFrag dialFrag3 = new windFrag();
                dialFrag3.show(fm,"windFrag");

                //======DialogFragment로 값보내는 코드 -> Sharefreprence로 대체되었습니다.=====
//                //start
//                FragmentManager fm = getFragmentManager();
//                windFrag dialFrag3 = new windFrag();
//                Bundle args = new Bundle();
//
//                // DB에서 값 가져오기
//
//                //서버에서 썬 체크된 값 받아오기
//                final DocumentReference docRef = db.collection("arduinoSensor").document("sensorData");
//                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.w(TAG, "Listen failed.", e);
//                            return;
//                        }
//
//                        if (snapshot != null && snapshot.exists()) {
//                            windwind = snapshot.get("wind").toString();
//                            Log.d("잘나오나요", windwind);
//
//
//                        } else {
//                            Log.d(TAG, "Current data: null");
//                        }
//                    }
//                });
//
//                args.putString("switch", windwind);
//                dialFrag3.setArguments(args);
//
//                //end
//                dialFrag3.show(fm,"windFrag");

            }
        });

        ibtn_sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                sunFrag dialFrag3 = new sunFrag();
                dialFrag3.show(fm,"sunFrag");

            }

        });

        return fragment;
    }

}

