package com.example.submission4.model.detail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.basisdata.MovieHelper;
import com.example.submission4.basisdata.TvHelper;
import com.example.submission4.model.TvShow.ResultsTv;
import com.example.submission4.model.movie.ResultsItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    public static String DETAIL_DATA_KEY = "detail_data";
    public static String TYPE_DATA_KEY = "jenis_data";
    public String typeOfData;
    private String title, year, description, poster;
    private int id;
    private Menu mainMenu = null;
    private Boolean isFavorite = false;
    private ResultsItem movie;
    private ResultsTv tvShow;
    private MovieHelper movieHelper;
    private TvHelper tvHelper;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtJudul = findViewById(R.id.dtljudul);
        TextView txtdesc = findViewById(R.id.dtldesc);
        TextView txtdate = findViewById(R.id.dtlyear);
        ImageView imgposter = findViewById(R.id.dtlposter);


        typeOfData = getIntent().getStringExtra(TYPE_DATA_KEY);

        if (typeOfData.equals("movie")) {
            movieHelper = MovieHelper.getInstance(getApplicationContext());
            movieHelper.open();

            movie = getIntent().getParcelableExtra(DETAIL_DATA_KEY);
            id = movie.getId();
            title = movie.getTitle();
            year = movie.getReleaseDate();
            description = movie.getOverview();
            poster = movie.getPosterPath();
            setTitle(title);
        } else if (typeOfData.equals("tv")) {
            tvHelper = TvHelper.getInstance(getApplicationContext());
            tvHelper.open();

            tvShow = getIntent().getParcelableExtra(DETAIL_DATA_KEY);
            id = tvShow.getId();
            title = tvShow.getName();
            year = tvShow.getFirstAirDate();
            description = tvShow.getOverview();
            poster = tvShow.getPosterPath();
            setTitle(title);
        }

        txtJudul.setText(title);
        txtdesc.setText(description);
        txtdate.setText(year);

        String basedUrlImage = "https://image.tmdb.org/t/p/original";
        Glide.with(this).load(basedUrlImage + poster).into(imgposter);

         favoriteState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.add_to_favorite:
                if (isFavorite){
                    removeFromfavorite();
                }else {
                    addToFavorite();
                }
                isFavorite = !isFavorite;
                setFavorite();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        if (isFavorite){
            mainMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.like));
        }else {
            mainMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.disslike));
        }
    }

    private void addToFavorite() {
        if (typeOfData.equals("movie")){
            long result = movieHelper.insertMovie(movie);
            if (!(result > 0)){
                Toast.makeText(DetailActivity.this, R.string.favorite_fail, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(DetailActivity.this,R.string.favorite_success, Toast.LENGTH_SHORT).show();
            }
        }else if (typeOfData.equals("tv")){
            long result = tvHelper.insertTv(tvShow);
            if (!(result > 0)){
                Toast.makeText(DetailActivity.this, R.string.favorite_fail, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DetailActivity.this, R.string.favorite_success, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void removeFromfavorite() {
        if (typeOfData.equals("movie")){
            long result = movieHelper.deleteMovie(id);
            if (!(result > 0)){
                Toast.makeText(DetailActivity.this, R.string.request_fail, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(DetailActivity.this,R.string.request_success, Toast.LENGTH_SHORT).show();
            }
        }else if (typeOfData.equals("tv")){
            long result = tvHelper.deleteTv(id);
            if (!(result > 0)){
                Toast.makeText(DetailActivity.this, R.string.request_fail, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(DetailActivity.this, R.string.request_success, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void favoriteState() {
        if (typeOfData.equals("movie")){

            ResultsItem resultsItem = movieHelper.getMovieById(id);

            if (resultsItem != null){
                Log.d("detail", "favoriteState: founded");
                Log.d("detail", "favoriteState: "+ resultsItem);

                List<ResultsItem> listmovieitem = new ArrayList<>();
                listmovieitem.add(0, resultsItem);

                if (listmovieitem.isEmpty()){
                    isFavorite = false;
                    Log.d("detail", "favoriteState: favorite data cant be founded");
                }else {
                    isFavorite = true;
                }
            }else {
                isFavorite = false;
                Log.d("detail", "favoriteState: favorite data cant be founded");
            }
        }else if (typeOfData.equals("tv")){
            ResultsTv resultsItem = tvHelper.getTvById(id);

            if (resultsItem != null){
                Log.d("detail", "favoriteState: founded");
                Log.d("detail", "favoriteState: "+ resultsItem);

                List<ResultsTv> listtvitem = new ArrayList<>();

                if (listtvitem.isEmpty()){
                    isFavorite = false;
                    Log.d("detail", "favoriteState: favorite data cant be founded");
                }else {
                    isFavorite = true;
                }
            }else {
                isFavorite = false;
                Log.d("detail", "favoriteState: favorite data cant be founded");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        mainMenu = menu;
        setFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (typeOfData.equals("movie")){
            movieHelper.close();
        }else if (typeOfData.equals("tv")){
            tvHelper.close();
        }
    }
}
