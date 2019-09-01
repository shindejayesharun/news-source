package com.shindejayesharun.newssource.news.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.adapter.GooglesNewsAdapter;
import com.shindejayesharun.newssource.news.model.NewsListViewModel;
import com.shindejayesharun.newssource.news.adapter.GoogleNewsAdapter;
import com.shindejayesharun.newssource.news.adapter.NewsAdapter;
import com.shindejayesharun.newssource.news.model.GoogleNewsModel;
import com.shindejayesharun.newssource.news.model.NewsFeedModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BBCNewsListFragment extends Fragment {

    private NewsListViewModel mViewModel;
    private RecyclerView newsRecyclerview;
    ArrayList<NewsFeedModel> newsFeedModels;
    ArrayList<GoogleNewsModel> googleNewsModels;
    ProgressDialog dialog;

    public static BBCNewsListFragment newInstance() {
        return new BBCNewsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        dialog=new ProgressDialog(getContext());
        newsFeedModels = new ArrayList<>();
        googleNewsModels = new ArrayList<>();
        newsRecyclerview = view.findViewById(R.id.newsRecyclerview);
        newsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        //new FetchFeedTask().execute((Void) null);
        //new jsoupGooglefetch().execute();
        new jsoupBBCHindi().execute();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsListViewModel.class);

    }


    class jsoupGooglefetch extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Document document = null;
            try {
                //document = Jsoup.connect("https://news.google.com/?hl=en-IN&gl=IN&ceid=IN:en").get();
                document = Jsoup.connect("https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen").get();
                //document = Jsoup.connect("https://news.google.com/search?q=india&hl=en-IN&gl=IN&ceid=IN%3Aen").get();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements headlines = document.select("a.wmzpFf");
            Elements articles = document.select("div.xrnccd");
            try {
                Element elementNewsSource = articles.get(0).select("time.WW6dff").get(0);
                Log.e("titles", elementNewsSource.childNode(0).toString());

                for (int i = 0; i < articles.size(); i++) {
                    GoogleNewsModel googleNewsModel = new GoogleNewsModel();
                    if (checkElementSizeNotZero(articles.get(i).select("h3.ipQwMb").select("a.DY5T1d"))) {
                        googleNewsModel.setHeadLine(articles.get(i).select("h3.ipQwMb").select("a.DY5T1d").get(0).childNode(0).toString());
                        googleNewsModel.setHeadLineLink("https://news.google.com/"+articles.get(i).select("h3.ipQwMb").select("a.DY5T1d").get(0).getElementsByAttribute("href").attr("href"));
                        googleNewsModel.setHeadLineSrc(articles.get(i).select("a.wEwyrc").get(0).childNode(0).toString());
                        googleNewsModel.setHeadLineTime(articles.get(i).select("time.WW6dff").get(0).childNode(0).toString());
                    }
                    if (checkElementSizeNotZero(articles.get(i).select("img.tvs3Id"))) {
                        googleNewsModel.setHeadLineImage(articles.get(i).select("img.tvs3Id").get(0).getElementsByAttribute("src").attr("src"));
                    }
                    if (checkElementSizeNotZero(articles.get(i).select("h4.ipQwMb").select("a.DY5T1d"))) {
                        googleNewsModel.setSubHeadLine(articles.get(i).select("h4.ipQwMb").select("a.DY5T1d").get(0).childNode(0).toString());
                        googleNewsModel.setSubHeadLineLink("https://news.google.com/"+articles.get(i).select("h4.ipQwMb").select("a.DY5T1d").get(0).getElementsByAttribute("href").attr("href"));
                        googleNewsModel.setHeadLineSrc(articles.get(i).select("a.wEwyrc").get(0).childNode(0).toString());
                        googleNewsModel.setSubHeadLineTime(articles.get(i).select("time.WW6dff").get(0).childNode(0).toString());
                    }
                    /*if(i%5==0){
                        googleNewsModels.add(new GoogleNewsModel());
                    }*/
                    googleNewsModels.add(googleNewsModel);
                }

            } catch (Exception e) {

            }
            //Log.e("subtitles",subtitles.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            googleNewsModels.size();
            newsRecyclerview.setAdapter(new GooglesNewsAdapter(getContext(), googleNewsModels));
        }
    }


    class jsoupBBCHindi extends AsyncTask<Void, Void, Boolean> {

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
                document = Jsoup.connect("https://www.bbc.com/hindi").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements articles = document.select("div.distinct-component-group");
            try {
                /*Elements element = articles.get(0).select("img");
                Log.e("data", element.get(0).getElementsByAttribute("src").attr("src"));
*/
                for (int i = 0; i < articles.size(); i++) {
                    GoogleNewsModel googleNewsModel = new GoogleNewsModel();
                    if(checkElementSizeNotZero(articles.get(i).select("a.faux-block-link__overlay-link"))){
                        googleNewsModel.setHeadLine(articles.get(i).select("a.faux-block-link__overlay-link").get(0).childNode(0).toString());
                        googleNewsModel.setHeadLineLink("https://www.bbc.com"+articles.get(i).select("a.faux-block-link__overlay-link").get(0).getElementsByAttribute("href").attr("href"));
                    }
                    if(checkElementSizeNotZero(articles.get(i).select("div.date"))){
                        googleNewsModel.setHeadLineTime(articles.get(i).select("div.date").get(0).childNode(0).toString());
                    }
                    if(checkElementSizeNotZero(articles.get(i).select("p"))) {
                        googleNewsModel.setSubHeadLine(articles.get(i).select("p").get(0).childNode(0).toString());
                    }

                    if (checkElementSizeNotZero(articles.get(i).select("div.js-delayed-image-load"))) {
                        googleNewsModel.setHeadLineImage(articles.get(i).select("div.js-delayed-image-load").get(0).getElementsByAttribute("data-src").attr("data-src"));
                    }
                    /*if(googleNewsModel.getHeadLineImage().length()==0){
                        if (checkElementSizeNotZero(articles.get(0).select("img"))) {
                            googleNewsModel.setHeadLineImage(articles.get(0).select("img").get(0).getElementsByAttribute("src").attr("src"));
                        }
                    }*/

                    googleNewsModels.add(googleNewsModel);
                }

            } catch (Exception e) {
                Log.e("error","-----");
                dialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            googleNewsModels.size();
            newsRecyclerview.setAdapter(new GoogleNewsAdapter(getContext(), googleNewsModels));
        }
    }

    private boolean checkElementSizeNotZero(Elements elements) {
        if (elements.size() == 0)
            return false;
        return true;
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
