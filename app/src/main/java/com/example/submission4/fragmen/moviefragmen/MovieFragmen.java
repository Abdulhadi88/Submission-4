package com.example.submission4.fragmen.moviefragmen;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submission4.R;
import com.example.submission4.fragmen.adapter.MovieAdapter;
import com.example.submission4.model.detail.DetailActivity;
import com.example.submission4.model.movie.ResultsItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragmen extends Fragment {

    private MovieAdapter movieAdapter;
    private RecyclerView recMov;
    private ProgressBar pgMov;



    public MovieFragmen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getView() != null ? getView():
                inflater.inflate(R.layout.fragment_fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MovieViewModel movieViewModel = ViewModelProviders.of(MovieFragmen.this)
                .get(MovieViewModel.class);
        movieViewModel.getListMovie().observe(this, getMovie);

        recMov = view.findViewById(R.id.recmov);
        pgMov = view.findViewById(R.id.progressbar);

        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();

        movieViewModel.setListMovie();
        showloading(true);
        showResiclerlist();
    }

    private Observer<List<ResultsItem>> getMovie = new Observer<List<ResultsItem>>() {
        @Override
        public void onChanged(List<ResultsItem> resultsItems) {
            if (resultsItems != null){
                movieAdapter.setListData(resultsItems);
                showloading(false);
            }
        }
    };

    private void showResiclerlist() {
        recMov.setLayoutManager(new LinearLayoutManager(getActivity()));
        recMov.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsItem data) {
                showSelectedmovie(data);
            }
        });
    }

    private void showSelectedmovie(ResultsItem movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_DATA_KEY, movie);
        intent.putExtra(DetailActivity.TYPE_DATA_KEY, "movie");
        startActivity(intent);
    }

    private void showloading(boolean state) {
        if (state){
            pgMov.setVisibility(View.VISIBLE);
        }else {
            pgMov.setVisibility(View.GONE);
        }
    }
}
