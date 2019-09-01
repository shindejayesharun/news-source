package com.shindejayesharun.newssource.news.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.adapter.GoogleNewsAdapter;
import com.shindejayesharun.newssource.news.adapter.GooglesNewsAdapter;
import com.shindejayesharun.newssource.news.fragment.NewsListFragment;
import com.shindejayesharun.newssource.news.model.GoogleNewsModel;
import com.shindejayesharun.newssource.news.model.NewsFeedModel;
import com.shindejayesharun.newssource.news.utility.PrefManager;
import com.shindejayesharun.newssource.news.utility.RssExtract;
import com.shindejayesharun.newssource.news.utility.Utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsListActivity extends AppCompatActivity {

    ProgressDialog dialog;
    private RecyclerView newsRecyclerview;
    ArrayList<NewsFeedModel> newsFeedModels;
    ArrayList<GoogleNewsModel> googleNewsModels;
    private String url;
    ImageView imgBackArrow;
    PrefManager prefManager;
    int selectedLanguage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);


        url = getIntent().getStringExtra("url");
        prefManager=new PrefManager(this);
        selectedLanguage=prefManager.getLanguage();
        init();

        dialog = new ProgressDialog(this);
        newsFeedModels = new ArrayList<>();
        googleNewsModels = new ArrayList<>();
        newsRecyclerview = findViewById(R.id.newsRecyclerview);
        newsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        if(Utility.getConnectionStatus(this)) {
            new jsoupGooglefetch().execute();
        }else {
            Utility.alertCheckInternet(this);
        }


    }

    private void init() {
        imgBackArrow = findViewById(R.id.imgBackArrow);

        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    class jsoupGooglefetch extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Document document = null;
            try {
                document = Jsoup.connect(url).get();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(document!=null) {
                try {
                    if(selectedLanguage==0) {
                        RssExtract.googleExtact(document, googleNewsModels);
                    }else if(selectedLanguage==1){
                        RssExtract.bbcExtract(document, googleNewsModels,false);
                    }else if(selectedLanguage==2){
                        RssExtract.googleExtact(document, googleNewsModels);
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                }
            }
            //Log.e("subtitles",subtitles.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            googleNewsModels.size();
            newsRecyclerview.setAdapter(new GooglesNewsAdapter(NewsListActivity.this, googleNewsModels));
        }
    }

    private boolean checkElementSizeNotZero(Elements elements) {
        if (elements.size() == 0)
            return false;
        return true;
    }
}
