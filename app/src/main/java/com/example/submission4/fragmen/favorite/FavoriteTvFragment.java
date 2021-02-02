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
import com.example.submission4.basisdata.TvHelper;
import com.example.submission4.fragmen.adapter.FavoriteTvAdapter;
import com.example.submission4.fragmen.inter.LoadTvCallback;
import com.example.submission4.model.TvShow.ResultsTv;
import com.example.submission4.model.detail.DetailActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvFragment extends Fragment implements LoadTvCallback {
    private ProgressBar tvpgbar;
    private TvHelper tvHelper;
    private FavoriteTvAdapter favoriteTvAdapter;
    private static final String Extra_STATE = "EXTRA_STATE";


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getView() != null ? getView():
                inflater.inflate(R.layout.fragment_favorite_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.fac_rec_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        tvpgbar = view.findViewById(R.id.fav_tv_pb);

        tvHelper = TvHelper.getInstance(getActivity());
        try {
            tvHelper.open();
        }catch (SQLException e){
            e.printStackTrace();
        }

        favoriteTvAdapter = new FavoriteTvAdapter(getActivity());
        recyclerView.setAdapter(favoriteTvAdapter);

        favoriteTvAdapter.setOnitemclickCallBack(new FavoriteTvAdapter.OnitemclickCallBack() {
            @Override
            public void onItemClicked(ResultsTv data) {
                showSelectedData(data);
            }
        });

        if (savedInstanceState == null){
            Log.d("favoritetv", "onViewCreated: saved instance kosong");
            new FavoriteTvFragment.LoadTvAsyn(tvHelper, this).execute();
        }else {
            ArrayList<ResultsTv> list = savedInstanceState.getParcelableArrayList(Extra_STATE);
            if (list != null){
                favoriteTvAdapter.setListTv(list);
            }
            Log.d("favoritetv", "onViewCreated: saved instance terbaca" + list);
        }
    }

    private void showSelectedData(ResultsTv data) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_DATA_KEY, data);
        intent.putExtra(DetailActivity.TYPE_DATA_KEY, "tv");
        startActivity(intent);

    }

    @Override
    public void preExecute() {
        new Runnable(){
            @Override
            public void run() {
                tvpgbar.setVisibility(View.INVISIBLE);
            }
        };
        Log.d("favoritetv", "preExecute: masuk");
    }

    @Override
    public void postExecute(ArrayList<ResultsTv> resultsItems) {
        tvpgbar.setVisibility(View.INVISIBLE);
        favoriteTvAdapter.setListTv(resultsItems);
        Log.d("favoritetv", "postExecute: "+ resultsItems.toString());
    }

    private class LoadTvAsyn extends AsyncTask<Void, Void, ArrayList<ResultsTv>>{
        private final WeakReference<TvHelper>tvHelperWeakReference;
        private final WeakReference<LoadTvCallback>loadTvCallbackWeakReference;

        LoadTvAsyn(TvHelper tvHelper, LoadTvCallback loadTvCallback){
            tvHelperWeakReference = new WeakReference<>(tvHelper);
            loadTvCallbackWeakReference = new WeakReference<>(loadTvCallback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadTvCallbackWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<ResultsTv> doInBackground(Void... voids) {
            return tvHelperWeakReference.get().getAllTv();
        }

        @Override
        protected void onPostExecute(ArrayList<ResultsTv> resultsTvs) {
            super.onPostExecute(resultsTvs);
            loadTvCallbackWeakReference.get().postExecute(resultsTvs);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }
}
