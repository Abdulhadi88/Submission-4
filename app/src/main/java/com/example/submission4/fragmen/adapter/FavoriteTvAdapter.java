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
import com.example.submission4.fragmen.favorite.FavoriteTvFragment;
import com.example.submission4.model.TvShow.ResultsTv;
import com.example.submission4.model.TvShow.TvItem;

import java.util.ArrayList;

public class FavoriteTvAdapter extends RecyclerView.Adapter<FavoriteTvAdapter.ViewHolder> {

    private ArrayList<ResultsTv> listTv = new ArrayList<>();
    private Context context;
    private OnitemclickCallBack onitemclickCallBack;

    ArrayList<ResultsTv> getListTv(){
        return listTv;
    }
    public FavoriteTvAdapter(Context activity){
        context = activity;
    }
    public void setListTv(ArrayList<ResultsTv> listTv){
        if (listTv.size() > 0){
            this.listTv.clear();
        }
        this.listTv.addAll(listTv);
        notifyDataSetChanged();
    }

    public void addItem(ResultsTv note){
        this.listTv.add(note);
        notifyItemInserted(listTv.size() - 1);
    }

    public void updateItem(int position, ResultsTv note){
        this.listTv.add(position, note);
        notifyItemChanged(position, note);
    }
    public void removeItem(int posotion){
        this.listTv.remove(posotion);
        notifyItemRemoved(posotion);
        notifyItemRangeChanged(posotion, listTv.size());
    }

    public void setOnitemclickCallBack(OnitemclickCallBack onitemclickCallBack){
        this.onitemclickCallBack = onitemclickCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_temeplet, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvTvJudul.setText(listTv.get(position).getName());
        holder.tvTVdetail.setText(listTv.get(position).getOverview());

        String baseUrlImg = "https://image.tmdb.org/t/p/original";
        Glide.with(context).load(baseUrlImg + listTv.get(position).getPosterPath())
                .into(holder.tvImgposter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclickCallBack.onItemClicked(listTv.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTvJudul, tvTVdetail;
        ImageView tvImgposter;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTvJudul = itemView.findViewById(R.id.tittletmp);
            tvTVdetail = itemView.findViewById(R.id.desctmp);
            tvImgposter = itemView.findViewById(R.id.postertmp);
        }
    }

    public interface OnitemclickCallBack {
        void onItemClicked(ResultsTv data);
    }


}
