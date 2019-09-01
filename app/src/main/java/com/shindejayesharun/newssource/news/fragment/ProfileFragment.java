package com.shindejayesharun.newssource.news.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.adapter.GoogleNewsAdapter;
import com.shindejayesharun.newssource.news.adapter.NewsAdapter;
import com.shindejayesharun.newssource.news.model.GoogleNewsModel;
import com.shindejayesharun.newssource.news.model.NewsFeedModel;
import com.shindejayesharun.newssource.news.model.NewsListViewModel;

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

public class ProfileFragment extends Fragment implements View.OnClickListener {


    ImageView imgSetting;
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        imgSetting=view.findViewById(R.id.imgSetting);
        imgSetting.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.imgSetting:
                Intent i=new Intent(getContext(),SettingActivity.class);
                startActivity(i);
                break;
        }
    }
}
