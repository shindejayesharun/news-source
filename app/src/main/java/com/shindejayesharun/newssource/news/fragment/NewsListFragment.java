package com.shindejayesharun.newssource.news.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.adapter.GooglesNewsAdapter;
import com.shindejayesharun.newssource.news.adapter.NewsAdapter;
import com.shindejayesharun.newssource.news.model.GoogleNewsModel;
import com.shindejayesharun.newssource.news.model.NewsFeedModel;
import com.shindejayesharun.newssource.news.utility.PrefManager;
import com.shindejayesharun.newssource.news.utility.RssExtract;
import com.shindejayesharun.newssource.news.utility.Utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsListFragment extends Fragment {
    ProgressDialog dialog;
    private RecyclerView newsRecyclerview;
    ArrayList<NewsFeedModel> newsFeedModels;
    ArrayList<GoogleNewsModel> googleNewsModels;
    PrefManager prefManager;
    Integer selectedLanguage=0;

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        dialog = new ProgressDialog(getContext());
        newsFeedModels = new ArrayList<>();
        googleNewsModels = new ArrayList<>();
        newsRecyclerview = view.findViewById(R.id.newsRecyclerview);
        newsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        prefManager=new PrefManager(getContext());
        selectedLanguage=prefManager.getLanguage();


        if(Utility.getConnectionStatus(getContext())) {
            new jsoupGooglefetch().execute();
        }else {
            Utility.alertCheckInternet(getContext());
        }


        return view;
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
                if(selectedLanguage==0) {
                    document = Jsoup.connect("https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen").get();
                }else if(selectedLanguage==1){
                    document = Jsoup.connect("https://www.bbc.com/hindi").get();
                }else if(selectedLanguage==2){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(document!=null) {
                try {
                    if(selectedLanguage==0) {
                        RssExtract.googleExtact(document, googleNewsModels);
                    }else if(selectedLanguage==1){
                        RssExtract.bbcExtract(document, googleNewsModels, true);
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
            newsRecyclerview.setAdapter(new GooglesNewsAdapter(getContext(), googleNewsModels));
        }
    }



    class jsoupBBCHindi extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Document document = null;
            try {
                document = Jsoup.connect("https://www.bbc.com/hindi").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                /*Elements element = articles.get(0).select("img");
                Log.e("data", element.get(0).getElementsByAttribute("src").attr("src"));
*/


            } catch (Exception e) {
                Log.e("error", "-----");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            googleNewsModels.size();
            newsRecyclerview.setAdapter(new GooglesNewsAdapter(getContext(), googleNewsModels));
        }
    }




    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://timesofindia.indiatimes.com/rssfeedstopstories.cms?x=1");
                InputStream inputStream = url.openConnection().getInputStream();
                newsFeedModels = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                newsRecyclerview.setAdapter(new NewsAdapter(getContext(), newsFeedModels));
            } else {
                Toast.makeText(getContext(),
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public ArrayList<NewsFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        boolean isItem = false;
        ArrayList<NewsFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MainActivity", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }

                if (title != null && link != null && description != null) {
                    if (isItem) {
                        NewsFeedModel item = new NewsFeedModel(title, link, description);
                        items.add(item);
                    }


                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }


}
