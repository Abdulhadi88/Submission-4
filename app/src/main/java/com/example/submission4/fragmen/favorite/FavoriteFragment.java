package com.example.submission4.fragmen.favorite;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.submission4.R;
import com.example.submission4.activity.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {



    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return getView() != null? getView():
                inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new FavMovieFragment(), getResources().getString(R.string.movie));
        viewPagerAdapter.addFragment(new FavoriteTvFragment(), getResources().getString(R.string.tv_show));

        ViewPager viewPager = view.findViewById(R.id.fav_view_pager);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabs = view.findViewById(R.id.fav_tabs);
        tabs.setupWithViewPager(viewPager);


    }
}
