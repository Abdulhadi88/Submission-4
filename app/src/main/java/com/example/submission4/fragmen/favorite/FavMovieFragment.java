package com.example.submission4.fragmen.favorite;


import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.submission4.R;
import com.example.submission4.basisdata.MovieHelper;
import com.example.submission4.fragmen.adapter.FavoriteMovieAdapter;
import com.example.submission4.fragmen.adapter.MovieAdapter;
import com.example.submission4.fragmen.inter.LoadMovieCallback;
import com.example.submission4.model.detail.DetailActivity;
import com.example.submission4.model.movie.ResultsItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment implements LoadMovieCallback {
    private ProgressBar mprogressBar;
    private MovieHelper movieHelper;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return getView() != null ? getView():
                inflater.inflate(R.layout.fragment_fav__movie_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFav = view.findViewById(R.id.fav_rec_mov);
        rvFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFav.setHasFixedSize(true);
        mprogressBar = view.findViewById(R.id.fav_mov_pb);

        movieHelper = MovieHelper.getInstance(getActivity());
        try {
            movieHelper.open();
        }catch (SQLException e){
            e.printStackTrace();
        }

        favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity());
        rvFav.setAdapter(favoriteMovieAdapter);

        favoriteMovieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItem data) {
                showSelectedData(data);
            }
        });

        if(savedInstanceState == null){
            Log.d("favoriteMovie", "onViewCreated: saved instance kosong");
            new LoadMovieAsyn(movieHelper, this).execute();
        }else {
            Log.d("favoritemovie", "onViewCreated: data di temukan");
            ArrayList<ResultsItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list !=null){
                favoriteMovieAdapter.setListMov(list);
            }
        }
    }
    public void showSelectedData(ResultsItem movie){
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_DATA_KEY, movie);
        intent.putExtra(DetailActivity.TYPE_DATA_KEY,"movie");
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favoriteMovieAdapter.getListMov());
    }

    @Override
    public void preExecute() {
        new Runnable() {
            @Override
            public void run() {
                mprogressBar.setVisibility(View.VISIBLE);
            }
        };
        Log.d("favoritemovie", "preExecute: masuk");
    }

    @Override
    public void postExecute(ArrayList<ResultsItem> listMov){
        mprogressBar.setVisibility(View.INVISIBLE);
        favoriteMovieAdapter.setListMov(listMov);
        Log.d("favoritemovie", "postExecute: " + listMov.toString());
    }

    private class LoadMovieAsyn extends AsyncTask<Void, Void, ArrayList<ResultsItem>>{
        private final WeakReference<MovieHelper> movieHelperWeakReference;
        private final WeakReference<LoadMovieCallback> loadMovieCallbackWeakReference;

        LoadMovieAsyn(MovieHelper movieHelper, LoadMovieCallback loadMovieCallback){
            movieHelperWeakReference = new WeakReference<>(movieHelper);
            loadMovieCallbackWeakReference = new WeakReference<>(loadMovieCallback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadMovieCallbackWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<ResultsItem> doInBackground(Void... voids) {
            return movieHelperWeakReference.get().getAllMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<ResultsItem> resultsItems) {
            super.onPostExecute(resultsItems);
            loadMovieCallbackWeakReference.get().postExecute(resultsItems);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
