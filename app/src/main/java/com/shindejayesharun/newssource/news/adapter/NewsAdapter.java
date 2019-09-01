package com.shindejayesharun.newssource.news.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.model.NewsFeedModel;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    Context mContext;
    ArrayList<NewsFeedModel> newsFeedModels;

    public NewsAdapter(Context mContext, ArrayList<NewsFeedModel> newsFeedModels) {
        this.mContext = mContext;
        this.newsFeedModels = newsFeedModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewsFeedModel newsFeedModel=newsFeedModels.get(position);
        holder.tvTitle.setText(newsFeedModel.title);
        holder.tvDesc.setText(Html.fromHtml(newsFeedModel.description));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "url"+newsFeedModel.link, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsFeedModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.titleText);
            tvDesc=itemView.findViewById(R.id.descText);
        }
    }
}
