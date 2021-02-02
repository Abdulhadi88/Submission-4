package com.example.submission4.fragmen.moviefragmen;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.submission4.model.movie.ResponseMovie;
import com.example.submission4.model.movie.ResultsItem;
import com.example.submission4.network.ApiClient;
import com.example.submission4.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    public static final String API_KEY = "fd7e20f75623b68562c9fa0868470a7d";
    private MutableLiveData<List<ResultsItem>> listMovie = new MutableLiveData<>();

    MutableLiveData<List<ResultsItem>> getListMovie(){
        return listMovie;
    }

    void setListMovie(){
        ApiInterface apiInterFace = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseMovie> movieCall = apiInterFace.getMovie(API_KEY);
        movieCall.enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                listMovie.postValue(response.body().getResults());
                Log.d("onResponse: ", response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                Log.d("onFailure: ", t.getMessage());
            }
        });

    }

}
