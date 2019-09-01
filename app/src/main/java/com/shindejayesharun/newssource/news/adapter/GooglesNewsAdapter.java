package com.shindejayesharun.newssource.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.activity.WebViewActivity;
import com.shindejayesharun.newssource.news.model.GoogleNewsModel;

import java.util.ArrayList;
import java.util.List;

public class GooglesNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    ArrayList<GoogleNewsModel> googleNewsModels;

    public GooglesNewsAdapter(Context mContext, ArrayList<GoogleNewsModel> googleNewsModels) {
        this.mContext = mContext;
        this.googleNewsModels = googleNewsModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch(viewType){
            case 1:{
                View v = inflater.inflate(R.layout.item_google_news, parent, false);
                viewHolder = new MyViewHolder(v);
                break;
            }
            case 2:{
                View v = inflater.inflate(R.layout.item_banner, parent, false);
                viewHolder = new ViewHolderAdMob(v);
                break;
            }
        }
        return  viewHolder;
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvSource;
        ImageView imgLogo;
        CardView cardLogo;
        public MyViewHolder(View view) {
            super(view);
            tvTitle = itemView.findViewById(R.id.titleText);
            tvDesc = itemView.findViewById(R.id.descText);
            tvSource = itemView.findViewById(R.id.sourceText);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            cardLogo = itemView.findViewById(R.id.cardLogo);
        }
    }

    public static class ViewHolderAdMob extends RecyclerView.ViewHolder {
        public AdView mAdView;
        public ViewHolderAdMob(View view) {
            super(view);
            mAdView = (AdView) view.findViewById(R.id.ad_view);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);

            //
        }
    }




   /* @Override
    public int getItemViewType(int position) {
        return googleNewsModels.get(position).getViewType();
    }*/

    @Override
    public int getItemViewType(int position)
    {
        if ((position+1) % 5 == 0 && (position+1)!=1)
            return 2;
        return 1;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final GoogleNewsModel googleNewsModel = googleNewsModels.get(position);

        GoogleNewsModel model = googleNewsModels.get(holder.getAdapterPosition());

        switch(holder.getItemViewType()){
            case 1:{
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.tvTitle.setText(googleNewsModel.getHeadLine());
                viewHolder.tvDesc.setText(googleNewsModel.getSubHeadLine());
                viewHolder.tvSource.setText(googleNewsModel.getHeadLineSrc() + " - " + googleNewsModel.getHeadLineTime());

                if (googleNewsModel.getHeadLineImage() != null && !googleNewsModel.getHeadLineImage().isEmpty()) {
                    Glide.with(mContext).load(googleNewsModel.headLineImage).into(viewHolder.imgLogo);
                } else {
                    viewHolder.cardLogo.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleNewsModel.getHeadLineLink()));
                mContext.startActivity(browserIntent);*/
                        Intent i = new Intent(mContext, WebViewActivity.class);
                        i.putExtra("url", googleNewsModel.getHeadLineLink());
                        i.putExtra("title",googleNewsModel.getHeadLine());
                        mContext.startActivity(i);
                    }
                });
                break;
            }
            case 2:{
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return googleNewsModels.size();
    }




}
