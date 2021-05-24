package com.example.a3rd;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class mbtiFrag2 extends DialogFragment {

    static final String TAG = "mbtiFrag2";
    TextView tv_mbtiresult, tv_mbtiresult2, tv_mbtiresult3;
    Button btn_close;
    int mscore = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mbti_dialog2,container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tv_mbtiresult = view.findViewById(R.id.tv_bname);
        tv_mbtiresult2 = view.findViewById(R.id.tv_mbtiresult2);
        tv_mbtiresult3 = view.findViewById(R.id.tv_binfo);
        btn_close = view.findViewById(R.id.btn_bclose);

        Bundle scores = getArguments();
        mscore = scores.getInt("score");

        Log.d("점수받아오기",mscore+"");

        //빛, 습도, 통풍, 돌봄
        if(mscore<=10){
            tv_mbtiresult.setText("DHPW");
            tv_mbtiresult2.setText("'어둑하고 습하고 환기도 잘 안되는데 식물을 어떻게 키우지?'\n" +
                    "식물집사 팟스타그램이 다양한 콘텐츠와 프로그램으로 DHPW 유형의 식물집사님의 관심과 자신감을 더 북돋아 드리겠습니다. \n" +
                    "조금만 더 관심을 두고 바라봐 주신다면 다양한 초록 친구들과 함께 즐거운 가드닝 경험을 만들어나갈 수 있답니다.\n");
            tv_mbtiresult3.setText(" 추천식물 : 산세베리아,스투키,다육,틸란드시아 ");
        }else if(mscore<=20){
            tv_mbtiresult.setText("LDWA");
            tv_mbtiresult2.setText("빛과 바람이 잘 들며 적당히 습도가 높은 장소는 식물이 자라기 무척 좋은 환경이랍니다.\n" +
                    "게다가 적극적인 마음음까지 가진 LDWA물집사님께는  향기로운 식물로 힐링을 하시면 더 기분이 좋을것 같네요.\n" +
                    "겉흙이 마르면 하루 이틀 후 충분히 주세요.\n");
            tv_mbtiresult3.setText("추천식물 : 라벤더,히아신스,자스민,안스리움");
        }else if(mscore<=30){
            tv_mbtiresult.setText("LHWP");
            tv_mbtiresult2.setText("식물을 기르기에 적합한 환경 조건부터 가드닝을 대하는 적극적인 마음가짐까지!\n" +
                    "LHWP 유형의 식물 집사님은 대부분의 식물을 다 잘 키울수 있을 거에요.\n" +
                    "예쁜 꽃을 가까이에 두면 더 좋을 당신!\n" +
                    "일주일에 두번정도 주세요.\n");
            tv_mbtiresult3.setText("추천식물 : 레몬밤,페퍼민트,장미허브,로즈마리");
        }else if(mscore<=40){
            tv_mbtiresult.setText("DLWA");
            tv_mbtiresult2.setText("식물을 키우는데 베테랑이신 식물집사님! \n" +
                    "당신은 무엇을 키워도 잘자랄거에요 이미 완성되신분 \n");
            tv_mbtiresult3.setText("추천식물 : 치자,동양난,제라늄,알라만다");
        }

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                mbtiFrag mbtiFrag = new mbtiFrag();
//                mbtiFrag.show(fm,"mbtiFrag");
                getDialog().dismiss();


            }
        });


//        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //테스트데이터 입력

//        Map<String, Object> mbti = new HashMap<>();
//        mbti.put("question1",new MbtiVO("식물을 두려는 장소에 빛이 얼마나 오래 드나요?","거의 온 종일 빛이 들어요.",
//                "하루 6시간 정도 빛이 들어요","하루 2~3시간 정도 빛이 들어요","빛이 잘 들지 않아요"));
//
//        mbti.put("question2",new MbtiVO("평소, 창문을 얼마나 자주 열고 계시나요?","항상 열어두고 있어요.",
//                "하루 2-3시간 주기적으로 환기를 해요.","일주일에 2~3번, 주기적으로 환기를 해요.","생각날 때마다 한 번씩 환기해요."));
//
//        mbti.put("question3",new MbtiVO("식물을 두려는 장소는 얼마나 건조한가요?","가끔, 목이 칼칼할 정도로 건조해요.",
//                "습하지도, 건조하지도 않은 것 같아요.","약간 습한데, 불쾌하지는 않아요.","공기의 무게가 느껴질 정도로 습해요."));
//
//        mbti.put("question4",new MbtiVO("식물에 얼마나 자주 관심을 줄 수 있나요?","매일 들여다 볼 수 있어요.",
//                "일주일에 2~3번정도 들여다볼 수 있어요.","일주일에 한 번 정도 들여다볼 수 있어요.","생각날 때 마다 한번씩 들여다볼 수 있어요."));
//
//        mbti.put("question5",new MbtiVO("식물에 물주는 일, 좋아하시나요?","정말 좋아해요! 자주 주고 싶어요.",
//                "좋아하지만, 자주 주기는 번거로울 것 같아요.","귀찮지는 않지만, 종종 깜빡할 것 같아요.","물주기를 자주 잊을 것 같아요."));
//
//        mbti.put("question6",new MbtiVO("식물을 두려는 장소에 빛이 얼마나 강하게 드나요? (정오 기준)","조명을 켜지 않고도 책을 읽을 수 있을 정도에요.",
//                "조명을 켜지 않고도 일상생활을 할 수 있어요.","빛은 들지만, 조명을 켜지 않으면 일상생활은 무리가 있어요.","낮에도 새벽이나 저녁처럼 어두워요."));
//
//        mbti.put("question7",new MbtiVO("어떤 종류의 식물을 키우고 싶으신가요 ?","초본류의 관엽식물 (풀, 키가 작아요)",
//                "목본류의 관엽식물 (나무, 키가 커요)","꽃과 열매를 보는 식물","선인장, 다육식물"));
//
//        mbti.put("question8",new MbtiVO("식물을 둘 곳의 창문은 어떻게 생겼나요??","키를 훌쩍 넘는 통유리",
//                "키와 비슷한 베란다 슬라이딩 도어","키보다 살짝 작은 창문","환기가 겨우 가능한, 아주 작은 창문"));
//
//        mbti.put("question9",new MbtiVO("식식물을 키워본 경험이 있으신가요?","네, 지금도 잘 키우고 있어요.",
//                "키우고 있지만, 잘 키울 자신은 없어요.","한 번도 키워본 적이 없어요.","경험은 있지만, 지금은 식물을 키우고 있지 않아요."));
//
//        mbti.put("question10",new MbtiVO("아끼던 식물이 아프면, 어떻게 할 것 같으세요?","모르겠어요.",
//                "인터넷으로 정보를 검색해 볼 거예요.","'시간이 약이겠지...' 조금 더 지켜볼 거예요.","주변에 직접 물어보거나, 도움을 청할 거예요."));
//
//        db.collection("mbtitest").document("mbtidata2")
//                .update(mbti)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG,"데이터입력성공");
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "데이터입력실패", e);
//                    }
//                });
//
//        final DocumentReference docRef = db.collection("mbtitest").document("mbtidata2");
//        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
//                if (e != null) { Log.w(TAG, "Listen failed.", e);
//                return; }
//                if (snapshot != null && snapshot.exists()) {
//                    Log.d(TAG, "Current data: " + snapshot.getData());
//
//                    MbtiVO mvo = snapshot.get("question1",MbtiVO.class);
//                    Log.d("값 받아오기", snapshot.get("question1") + "");
//
//                }else {
//                    Log.d(TAG, "Current data: null");
//                }
//            }
//        });



        return view;
    }
}
