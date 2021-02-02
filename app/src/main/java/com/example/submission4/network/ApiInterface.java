package com.example.submission4.network;

import com.example.submission4.model.TvShow.TvItem;
import com.example.submission4.model.movie.ResponseMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
        Call<ResponseMovie> getMovie(@Query("api_key") String apiKey);

    @GET("discover/tv")
        Call<TvItem> getTv(@Query("api_key") String apiKey);

}
