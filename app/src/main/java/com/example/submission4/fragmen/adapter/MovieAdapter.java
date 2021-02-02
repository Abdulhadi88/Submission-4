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
import com.example.submission4.model.movie.ResultsItem;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<ResultsItem> listData = new ArrayList<>();
    private Context context;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {

        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListData(List<ResultsItem> listData) {
        this.listData.clear();
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_temeplet, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        holder.judulmov.setText(listData.get(position).getTitle());
        holder.descmov.setText(listData.get(position).getOverview());

        String basedUrlImage = "https://image.tmdb.org/t/p/original";
        Glide.with(context).load(basedUrlImage + listData.get(position).getPosterPath()).into(holder.postermov);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView judulmov, descmov;
        ImageView postermov;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            judulmov = itemView.findViewById(R.id.tittletmp);
            descmov = itemView.findViewById(R.id.desctmp);
            postermov = itemView.findViewById(R.id.postertmp);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(ResultsItem data);
    }
}
