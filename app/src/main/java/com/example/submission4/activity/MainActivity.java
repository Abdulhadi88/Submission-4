package com.example.submission4.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.submission4.R;
import com.example.submission4.fragmen.favorite.FavoriteFragment;
import com.example.submission4.fragmen.moviefragmen.MovieFragmen;
import com.example.submission4.fragmen.tvfragment.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Fragment pagecont = new MovieFragmen();
    public static final String KEY_FRAGMENT = "fragment";

    public BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.movie:
                    pagecont = new MovieFragmen();
                    openFragment(pagecont);
                    return true;
                case R.id.tvshow:
                    pagecont = new TvShowFragment();
                    openFragment(pagecont);
                    return true;
                case R.id.fav:
                    pagecont = new FavoriteFragment();
                    openFragment(pagecont);
                    return true;
            }
            return false;
        }
    };

    private void openFragment(Fragment pagecont) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framecont, pagecont).commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);
        BottomNavigationView navViewer = findViewById(R.id.bot_nav);
        navViewer.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);

        if (savedInstanceState == null){
            openFragment(pagecont);
        }else {
            pagecont.getFragmentManager().getFragment(savedInstanceState, KEY_FRAGMENT);
            openFragment(pagecont);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_language){
            Intent langinntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(langinntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT, pagecont);
        super.onSaveInstanceState(outState);
    }
}
