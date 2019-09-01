package com.shindejayesharun.newssource.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.activity.WebViewActivity;
import com.shindejayesharun.newssource.news.model.GoogleNewsModel;

import java.util.ArrayList;

import static android.drm.DrmStore.DrmObjectType.CONTENT;
import static java.util.GregorianCalendar.AD;

public class GoogleNewsAdapter extends RecyclerView.Adapter<GoogleNewsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<GoogleNewsModel> googleNewsModels;

    public GoogleNewsAdapter(Context mContext, ArrayList<GoogleNewsModel> googleNewsModels) {
        this.mContext = mContext;
        this.googleNewsModels = googleNewsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_google_news, parent, false);
       return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GoogleNewsModel googleNewsModel = googleNewsModels.get(position);
        if(googleNewsModel==null){

        }else {


            holder.tvTitle.setText(googleNewsModel.getHeadLine());
            holder.tvDesc.setText(googleNewsModel.getSubHeadLine());
            holder.tvSource.setText(googleNewsModel.getHeadLineSrc() + " - " + googleNewsModel.getHeadLineTime());

            if (googleNewsModel.getHeadLineImage() != null && !googleNewsModel.getHeadLineImage().isEmpty()) {
                Glide.with(mContext).load(googleNewsModel.headLineImage).into(holder.imgLogo);
            } else {
                holder.cardLogo.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleNewsModel.getHeadLineLink()));
                mContext.startActivity(browserIntent);*/
                    Intent i = new Intent(mContext, WebViewActivity.class);
                    i.putExtra("url", googleNewsModel.getHeadLineLink());
                    mContext.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return googleNewsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvSource;
        ImageView imgLogo;
        CardView cardLogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleText);
            tvDesc = itemView.findViewById(R.id.descText);
            tvSource = itemView.findViewById(R.id.sourceText);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            cardLogo = itemView.findViewById(R.id.cardLogo);
        }
    }


}
