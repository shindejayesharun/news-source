package com.shindejayesharun.newssource.news.utility;

import android.util.Log;

import com.shindejayesharun.newssource.news.model.GoogleNewsModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class RssExtract {

    private static boolean checkElementSizeNotZero(Elements elements) {
        if (elements.size() == 0)
            return false;
        return true;
    }

    public static ArrayList<GoogleNewsModel> googleExtact(Document document, ArrayList<GoogleNewsModel> googleNewsModels) {
        Elements headlines = document.select("a.wmzpFf");
        Elements articles = document.select("div.xrnccd");
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

            googleNewsModel.setViewType(1);
            googleNewsModels.add(googleNewsModel);
        }
        return googleNewsModels;
    }
    public static ArrayList<GoogleNewsModel> bbcExtract(Document document, ArrayList<GoogleNewsModel> googleNewsModels, boolean headline){
        Elements articles;
        if(headline) {
            articles = document.select("div.distinct-component-group");
        }else {
            articles = document.select("div.eagle-item");
        }
        for (int i = 0; i < articles.size(); i++) {
            GoogleNewsModel googleNewsModel = new GoogleNewsModel();
            if (checkElementSizeNotZero(articles.get(i).select("a.faux-block-link__overlay-link"))) {
                googleNewsModel.setHeadLine(articles.get(i).select("a.faux-block-link__overlay-link").get(0).childNode(0).toString());
                googleNewsModel.setHeadLineLink("https://www.bbc.com" + articles.get(i).select("a.faux-block-link__overlay-link").get(0).getElementsByAttribute("href").attr("href"));
            }
            if (checkElementSizeNotZero(articles.get(i).select("div.date"))) {
                googleNewsModel.setHeadLineTime(articles.get(i).select("div.date").get(0).childNode(0).toString());
            }
            if (checkElementSizeNotZero(articles.get(i).select("p"))) {
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
        return googleNewsModels;
    }

}
