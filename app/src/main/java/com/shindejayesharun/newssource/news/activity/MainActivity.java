package com.shindejayesharun.newssource.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.App;
import com.shindejayesharun.newssource.news.fragment.CollectionsFragment;
import com.shindejayesharun.newssource.news.fragment.NewsListFragment;
import com.shindejayesharun.newssource.news.fragment.ProfileFragment;
import com.shindejayesharun.newssource.news.utility.PrefManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    String urlSerarch="https://news.google.com/search?q=modi&hl=en-IN&gl=IN&ceid=IN%3Aen";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new NewsListFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    fragment = new CollectionsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadFragmentWithStack(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    ImageView imgSearch;
    AdView adView;
    LinearLayout navActionBar,navSearchBar;
    ImageView imgBackArrow;
    EditText searchView;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = new NewsListFragment();
        loadFragment(fragment);

        init();


    }

    private void init() {
        imgSearch=findViewById(R.id.imgSearch);
        navActionBar=findViewById(R.id.nav_action_bar);
        navSearchBar=findViewById(R.id.nav_search_bar);
        imgBackArrow=findViewById(R.id.imgBackArrow);
        searchView=findViewById(R.id.etSearchView);

        imgSearch.setOnClickListener(this);
        imgBackArrow.setOnClickListener(this);
        prefManager=new PrefManager(this);

        if(prefManager.getLanguage()==0){
            Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
        }else if(prefManager.getLanguage()==1){
            Toast.makeText(this, "Hindi", Toast.LENGTH_SHORT).show();
        }else if(prefManager.getLanguage()==2){
            Toast.makeText(this, "Marathi", Toast.LENGTH_SHORT).show();
        }




        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Intent s=new Intent(MainActivity.this,NewsListActivity.class);
                    s.putExtra("url","https://news.google.com/search?q="+searchView.getText().toString()+"&hl=en-IN&gl=IN&ceid=IN%3Aen");
                    startActivity(s);
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }


    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.imgSearch:
                    navActionBar.setVisibility(View.GONE);
                    navSearchBar.setVisibility(View.VISIBLE);
                break;
            case R.id.imgBackArrow:
                    navSearchBar.setVisibility(View.GONE);
                    navActionBar.setVisibility(View.VISIBLE);
                break;
        }
    }
}
