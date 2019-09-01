package com.shindejayesharun.newssource.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.activity.NewsListActivity;
import com.shindejayesharun.newssource.news.model.CollectionsModel;
import com.shindejayesharun.newssource.news.model.GoogleNewsModel;

import java.util.ArrayList;
import java.util.Random;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    Context mContext;
    ArrayList<CollectionsModel> collectionsModels;

    public CollectionAdapter(Context mContext, ArrayList<CollectionsModel> collectionsModels) {
        this.mContext = mContext;
        this.collectionsModels = collectionsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_collections, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CollectionsModel collectionsModel = collectionsModels.get(position);

        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.viewBgLogo.setBackground(draw);

        holder.tvTitle.setText(collectionsModel.getTitle());
        Glide.with(mContext).load(collectionsModel.getImage()).into(holder.imgLogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, NewsListActivity.class);
                i.putExtra("url",collectionsModel.getLink());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(collectionsModels==null){
            return 0;
        }
        return collectionsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgLogo;
        View viewBgLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBgLogo=itemView.findViewById(R.id.imgbackview);
            tvTitle = itemView.findViewById(R.id.titleText);
            imgLogo = itemView.findViewById(R.id.imgLogo);
        }
    }

}
