package com.example.submission4.fragmen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.basisdata.MovieHelper;
import com.example.submission4.model.movie.ResultsItem;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.MovieHolder> {

    private ArrayList<ResultsItem> listMov = new ArrayList<>();
    private Context mcontext;
    private MovieAdapter.OnItemClickCallback onItemClickCallback;

    public ArrayList<ResultsItem> getListMov(){
        return listMov;
    }

    public FavoriteMovieAdapter(Context context){
        this.mcontext = context;
    }

    public void setListMov(ArrayList<ResultsItem> listMov) {
        if (listMov.size()>0){
            this.listMov.clear();
        }
        this.listMov.addAll(listMov);

        notifyDataSetChanged();
    }

    public void addItem(ResultsItem note){
        this.listMov.add(note);
        notifyItemChanged(listMov.size() - 1);
    }

    public void updateItem(int position, ResultsItem note){
        this.listMov.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position){
        this.listMov.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listMov.size());
    }

    public void setOnItemClickCallback(MovieAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_view_temeplet, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.txtjudul.setText(listMov.get(position).getTitle());
        holder.txtdetail.setText(listMov.get(position).getTitle());

        String baseUrlImage = "https://image.tmdb.org/t/p/original";
        Glide.with(mcontext).load(baseUrlImage + listMov.get(position).getPosterPath())
                .into(holder.imgposter);
    }

    @Override
    public int getItemCount() {
        return listMov.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        TextView txtjudul, txtdetail;
        ImageView imgposter;

        MovieHolder(@NonNull View itemView) {
            super(itemView);
            txtjudul = itemView.findViewById(R.id.tittletmp);
            txtdetail = itemView.findViewById(R.id.desctmp);
            imgposter = itemView.findViewById(R.id.postertmp);
        }
    }
    public interface OnItemClickCallBack{
        void onItemClicked(ResultsItem data);
    }
}
