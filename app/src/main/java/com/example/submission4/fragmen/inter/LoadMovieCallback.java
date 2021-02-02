package com.example.submission4.fragmen.inter;

import com.example.submission4.model.movie.ResultsItem;

import java.util.ArrayList;

public interface LoadMovieCallback {

    void preExecute();
    void postExecute(ArrayList<ResultsItem> resultsItems);
}
