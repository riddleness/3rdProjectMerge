package com.example.a3rd;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a3rd.Navigation.Account.FragmentAccount;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.a3rd.ApplicationClass.getRetrofit;

public class Frag2 extends Fragment {
    private View view;
        // 검색기능 입니다.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_search, container, false);

        
        return view;
    }

    public static interface SearchActivityView {

        void SearchSuccess(SearchResponse searchResponse);

        void SearchFailure(String message);

    }

    public static interface SearchInterface {
        @GET("/users")
        Call<SearchResponse> getSearch(
                @Query("userId") String userId);

    }

    public static class SearchResponse {


        @SerializedName("result")
        private SearchResult[] searchResults;

        @SerializedName("isSuccess")
        private boolean isSuccess;

        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;

        public class SearchResult {
            @SerializedName("userIdx")
            private int userIdx;

            @SerializedName("userId")
            private String userId;

            @SerializedName("profileImgUrl")
            private String profileImgUrl;

            @SerializedName("name")
            private String name;

            @SerializedName("follow")
            private String follow;

            public int getUserIdx() {
                return userIdx;
            }

            public String getUserId() {
                return userId;
            }

            public String getProfileImgUrl() {
                return profileImgUrl;
            }

            public String getName() {
                return name;
            }

            public String getFollow() {
                return follow;
            }
        }


        public SearchResult[] getSearchResults() {
            return searchResults;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class FragmentSearch extends Fragment implements SearchActivityView {

        EditText et_search;
        ImageView btn_search;
        ImageView img_smallprofile;

        private ArrayList<SearchResponse.SearchResult> searchResultArrayList;
        private SearchAdapter searchAdapter;
        private RecyclerView mRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;


        public FragmentSearch() {
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_search,container,false);
            searchResultArrayList = new ArrayList<SearchResponse.SearchResult>();
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_search);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            searchAdapter = new SearchAdapter(searchResultArrayList,getActivity());
            mRecyclerView.setAdapter(searchAdapter);

            et_search = view.findViewById(R.id.search_bar);
            btn_search = view.findViewById(R.id.btn_search);
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userId = et_search.getText().toString();
                    tryGetSearch(userId);
                }
            });

            searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                @Override
                public void onItem(View v, int position) {
                    System.out.println("아이디 검색 , 클릭");
                    int userIdx = searchResultArrayList.get(position).getUserIdx();
                    Fragment fragment = new FragmentAccount(); // Fragment 생성
                    Bundle bundle = new Bundle();
                    bundle.putInt("userIdx", userIdx); // Key, Value
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

            return view;
        }

        private void tryGetSearch(String userId) {
            final SearchService searchService = new SearchService(this);
            searchService.getSearch(userId);
        }


        @Override
        public void SearchSuccess(SearchResponse searchResponse) {
            //검색 -> 나온 결과들 리사이클러뷰에 넣음
            System.out.println("검색 성공");
            for (SearchResponse.SearchResult r : searchResponse.getSearchResults())
                searchAdapter.addItem(r);
            searchAdapter.notifyDataSetChanged();

        }

        @Override
        public void SearchFailure(String message) {
            System.out.println("검색실패");
        }
    }

    public static class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
        private final Context context;
        private ArrayList<SearchResponse.SearchResult> SearchResponseArrayList;
        private OnItemClickListener mListener = null ;

        public class SearchViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout searchResult;
            public ImageView searchProfileImg;
            public TextView searchId;
            public TextView searchName;

            //view holder
            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                searchResult = (LinearLayout)itemView.findViewById(R.id.search_result);
                searchProfileImg = (ImageView)itemView.findViewById(R.id.search_profile_img);
                searchId = (TextView)itemView.findViewById(R.id.tv_search_id);
                searchName = (TextView)itemView.findViewById(R.id.tv_search_name);


                //결과값 Click -> 해당 유저 페이지로 넘어감
                searchResult.setOnClickListener(new View.OnClickListener(){
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

        //생성자
        public SearchAdapter(ArrayList<SearchResponse.SearchResult> searchResponseArrayList, Context context){
            this.SearchResponseArrayList = searchResponseArrayList;
            this.context = context;
        }


        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_search_item, parent, false);
            return new SearchViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(SearchViewHolder searchViewHolder, int position) {
            SearchViewHolder holder = (SearchViewHolder) searchViewHolder;
            holder.searchId.setText(SearchResponseArrayList.get(position).getUserId());
            holder.searchName.setText(SearchResponseArrayList.get(position).getName());

            Glide.with(context)
                    .load(SearchResponseArrayList.get(position).getProfileImgUrl())
                    .error(R.drawable.icon_default_profileimg)
                    .into(holder.searchProfileImg);

        }

        @Override
        public int getItemCount() {
            return SearchResponseArrayList.size();
        }


        public void addItem(SearchResponse.SearchResult r) {
            SearchResponseArrayList.add(r);
        }




        public interface OnItemClickListener{
            void onItem(View v, int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.mListener = listener;
        }
    }

    public static class SearchService {

        private final SearchActivityView mSearchActivityView;

        SearchService(final SearchActivityView searchActivityView) {
            this.mSearchActivityView = searchActivityView;
        }

        void getSearch(String userId) {
            final SearchInterface searchInterface = getRetrofit().create(SearchInterface.class);
            searchInterface.getSearch(userId).enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    final SearchResponse searchResponse = response.body();
                    if (searchResponse == null) {
                        mSearchActivityView.SearchFailure(null);
                        return;
                    }

                    mSearchActivityView.SearchSuccess(searchResponse);
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    mSearchActivityView.SearchFailure(null);
                    Log.e("test",t.toString());
                }
            });
        }
    }
}
