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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);


        url = getIntent().getStringExtra("url");

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
                Elements headlines = document.select("a.wmzpFf");
                Elements articles = document.select("div.xrnccd");
                try {
                    Element elementNewsSource = articles.get(0).select("time.WW6dff").get(0);
                    Log.e("titles", elementNewsSource.childNode(0).toString());

                    for (int i = 0; i < articles.size(); i++) {
                        GoogleNewsModel googleNewsModel = new GoogleNewsModel();
                        if (checkElementSizeNotZero(articles.get(i).select("h3.ipQwMb").select("a.DY5T1d"))) {
                            googleNewsModel.setHeadLine(articles.get(i).select("h3.ipQwMb").select("a.DY5T1d").get(0).childNode(0).toString());
                            googleNewsModel.setHeadLineLink("https://news.google.com/" + articles.get(i).select("h3.ipQwMb").select("a.DY5T1d").get(0).getElementsByAttribute("href").attr("href"));
                            googleNewsModel.setHeadLineSrc(articles.get(i).select("a.wEwyrc").get(0).childNode(0).toString());
                            googleNewsModel.setHeadLineTime(articles.get(i).select("time.WW6dff").get(0).childNode(0).toString());
                        }
                        if (checkElementSizeNotZero(articles.get(i).select("img.tvs3Id"))) {
                            googleNewsModel.setHeadLineImage(articles.get(i).select("img.tvs3Id").get(0).getElementsByAttribute("src").attr("src"));
                        }
                        if (checkElementSizeNotZero(articles.get(i).select("h4.ipQwMb").select("a.DY5T1d"))) {
                            googleNewsModel.setSubHeadLine(articles.get(i).select("h4.ipQwMb").select("a.DY5T1d").get(0).childNode(0).toString());
                            googleNewsModel.setSubHeadLineLink("https://news.google.com/" + articles.get(i).select("h4.ipQwMb").select("a.DY5T1d").get(0).getElementsByAttribute("href").attr("href"));
                            googleNewsModel.setHeadLineSrc(articles.get(i).select("a.wEwyrc").get(0).childNode(0).toString());
                            googleNewsModel.setSubHeadLineTime(articles.get(i).select("time.WW6dff").get(0).childNode(0).toString());
                        }
                        /*if (i %5 == 0 ){
                            googleNewsModels.add(null);
                        }*/
                        googleNewsModels.add(googleNewsModel);
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
