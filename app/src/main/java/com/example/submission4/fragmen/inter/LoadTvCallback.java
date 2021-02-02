package com.example.submission4.fragmen.inter;

import com.example.submission4.model.TvShow.ResultsTv;

import java.util.ArrayList;

public interface LoadTvCallback {

    void preExecute();
    void postExecute(ArrayList<ResultsTv> resultsItems);
}
