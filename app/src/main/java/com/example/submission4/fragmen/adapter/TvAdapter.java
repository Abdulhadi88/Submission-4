package com.example.submission4.fragmen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission4.R;
import com.example.submission4.model.TvShow.ResultsTv;

import java.util.ArrayList;
import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private List<ResultsTv> listTv = new ArrayList<>();
    private Context context;
    private OnItemClickCallback onItemClickCallback;

    public TvAdapter(FragmentActivity activity) {
        context = activity;
    }


    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListData(List<ResultsTv> listTv){
        this.listTv.clear();
        this.listTv.addAll(listTv);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_temeplet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.ViewHolder holder, int position) {
        holder.tvtittletv.setText(listTv.get(position).getName());
        holder.tvdetailtv.setText(listTv.get(position).getOverview());

        String basedurlimage = "https://image.tmdb.org/t/p/original";
        Glide.with(context).load(basedurlimage + listTv.get(position).getPosterPath())
                .into(holder.imgpostertv);

        holder.imgpostertv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTv.get(holder.getAdapterPosition()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvtittletv, tvdetailtv;
        ImageView imgpostertv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtittletv = itemView.findViewById(R.id.tittletmp);
            tvdetailtv = itemView.findViewById(R.id.desctmp);
            imgpostertv = itemView.findViewById(R.id.postertmp);

        }
    }


    public interface OnItemClickCallback {
        void onItemClicked(ResultsTv data);
    }
}
