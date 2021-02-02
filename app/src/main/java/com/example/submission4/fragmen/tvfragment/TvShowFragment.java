package com.example.submission4.fragmen.tvfragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.submission4.R;
import com.example.submission4.fragmen.adapter.TvAdapter;
import com.example.submission4.model.TvShow.ResultsTv;
import com.example.submission4.model.detail.DetailActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private TvAdapter tvAdapter;
    private RecyclerView rvTv;
    private ProgressBar pgTv;


    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TvShowViewModel tvShowViewModel = ViewModelProviders.of(TvShowFragment.this)
                .get(TvShowViewModel.class);
        tvShowViewModel.getTv().observe(this, getTv);

        rvTv = view.findViewById(R.id.rectv);
        pgTv = view.findViewById(R.id.progressbartv);

        tvAdapter = new TvAdapter(getActivity());
        tvAdapter.notifyDataSetChanged();

        tvShowViewModel.setListtv();
        showLoading(true);
        showRecyclerView();
    }

    Observer<List<ResultsTv>> getTv = new Observer<List<ResultsTv>>() {
        @Override
        public void onChanged(List<ResultsTv> resultsItems) {
            if (resultsItems != null){
                tvAdapter.setListData(resultsItems);
                showLoading(false);
            }
        }
    };

    private void showRecyclerView() {
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTv.setAdapter(tvAdapter);

        tvAdapter.setOnItemClickCallback(new TvAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(ResultsTv data) {
                showSelected(data);
            }
        });
    }

    private void showSelected(ResultsTv data){
        Intent intenttv = new Intent(getActivity(), DetailActivity.class);
        intenttv.putExtra(DetailActivity.DETAIL_DATA_KEY, data);
        intenttv.putExtra(DetailActivity.TYPE_DATA_KEY, "tv");
        startActivity(intenttv);

    }

    private void showLoading(boolean states) {
        if (states){
            pgTv.setVisibility(View.VISIBLE);
        }else {
            pgTv.setVisibility(View.GONE);
        }
    }
}
