package com.shindejayesharun.newssource.news.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    Context context;
    SharedPreferences sharedPreferences;
    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("SharedData", Context.MODE_PRIVATE);
    }


    public int getLanguage() {
        return sharedPreferences.getInt("language", 0);
    }
    public void setLanguage(int language) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("language", language);
        editor.apply();
    }


}
