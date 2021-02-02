package com.example.submission4.fragmen.tvfragment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission4.model.TvShow.ResultsTv;
import com.example.submission4.model.TvShow.TvItem;
import com.example.submission4.network.ApiClient;
import com.example.submission4.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowViewModel extends ViewModel {
    private static final String API_KEY = "fd7e20f75623b68562c9fa0868470a7d";
    private MutableLiveData<List<ResultsTv>> resultTv = new MutableLiveData<>();

    MutableLiveData<List<ResultsTv>> getTv(){
        return resultTv;
    }
    void setListtv(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TvItem> calltv = apiInterface.getTv(API_KEY);
        calltv.enqueue(new Callback<TvItem>() {
            @Override
            public void onResponse(Call<TvItem> call, Response<TvItem> response) {
                if (response.body() !=null){
                    resultTv.postValue(response.body().getResults());
                    Log.d("onResponseTv", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<TvItem> call, Throwable t) {
                Log.d("onFailureTv", t.getMessage());

            }
        });

    }
}
