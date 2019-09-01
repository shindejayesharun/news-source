package com.shindejayesharun.newssource.news.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.model.NewsListViewModel;
import com.shindejayesharun.newssource.news.adapter.CollectionAdapter;
import com.shindejayesharun.newssource.news.model.CollectionsModel;

import java.util.ArrayList;

public class CollectionsFragment extends Fragment {
    ProgressDialog dialog;
    private NewsListViewModel mViewModel;
    private RecyclerView collectionRecyclerview;
    ArrayList<CollectionsModel> collectionsModels;


    public static CollectionsFragment newInstance() {
        return new CollectionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        dialog=new ProgressDialog(getContext());

        collectionsModels = new ArrayList<>();
        collectionRecyclerview = view.findViewById(R.id.newsRecyclerview);
        collectionRecyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        setData();
        CollectionAdapter collectionAdapter=new CollectionAdapter(getContext(),collectionsModels);
        collectionRecyclerview.setAdapter(collectionAdapter);

        return view;
    }

    private void setData() {
        collectionsModels.add(new CollectionsModel("HeadLines","https://lh3.ggpht.com/16hfpPK4nois_ekV4s4PRXYinW3MN_5EnUNq0sdaMvkfVECIVrMa44HBUVxSWyRB4CjtDlDYvg=p","https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFZxYUdjU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("World","https://lh5.ggpht.com/MMoPETUaDbUB1bO3KAi3cKQ_lzssju3VEp9ZPauGIamgSniMr4nV2SVkVvG-rKUJSLQJP84q7EA=p","https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRGx1YlY4U0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("Local stories","https://lh3.googleusercontent.com/D5g44boSmliEh79IQfM-LqzIdG-EVoAc4FM6VJVo69GshEIiZ06iulL5Ggx03VfSoIe-U6vFjA=p","https://news.google.com/topics/CAAqEQgKIgtDQklTQWdnR0tBQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("Business","https://lh4.ggpht.com/A0UiqrMFOJnh5R_g7xhIrslGxotNEcyK5V15p5yJkUlrXMBGqGw3TkU0x06Yb0Q-72QXK9N9=p","https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRGx6TVdZU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("Technology","https://lh4.ggpht.com/0JsT7seg_L1MnpCuWZvJ6CJCHpkCawqEMdOr8Iw_NWjNUyTqWZJZNDbfa6kUGg-q1KN4FiUPaw=p","https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRGRqTVhZU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("Entertainment","https://lh6.ggpht.com/M-7V3aFj1BEw9EYBVHdLFmjCerci3j2MvsB43zu6-9iu-znG_WuOYrz5urJlStV5n59mW0WY5Q=p","https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNREpxYW5RU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("Sports","https://lh4.ggpht.com/NyQryeBboxLw72N3_3mAHBYMuWlMaZ1RJHGRdF30RDICgv2-9Jupc2GzY2XeVdgaV3nbNaXb=p","https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFp1ZEdvU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("Science","https://lh6.ggpht.com/fJKuBX6iUFA_nPU2_2bIXcrTf3osSPhgBCL0sGqR2pZo-P6uZpiSmHW098W4I-CQHmsxZBd4=p","https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSUwyMHZNRFp0Y1RjU0FtVnVHZ0pKVGlnQVAB?hl=en-IN&gl=IN&ceid=IN%3Aen"));
        collectionsModels.add(new CollectionsModel("Health","https://lh3.ggpht.com/r4EKXwEo49BFQUa3OVZ8FC3j6teq_hUrmGcO4fI8BqoBgWSVJyu6D-vvXs9wGO7e_BKMbeqo4dk=p","https://news.google.com/topics/CAAqIQgKIhtDQkFTRGdvSUwyMHZNR3QwTlRFU0FtVnVLQUFQAQ?hl=en-IN&gl=IN&ceid=IN%3Aen"));
    }


}
