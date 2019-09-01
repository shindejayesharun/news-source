package com.shindejayesharun.newssource.news.fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.activity.MainActivity;
import com.shindejayesharun.newssource.news.utility.PrefManager;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView changeLanguage;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        changeLanguage=findViewById(R.id.change_langage);
        prefManager=new PrefManager(this);

        changeLanguage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.change_langage:
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
                builderSingle.setIcon(R.mipmap.newspaper);
                builderSingle.setTitle("Select Language");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("English");
                arrayAdapter.add("Hindi");
                arrayAdapter.add("Marathi");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        prefManager.setLanguage(which);
                        Intent i=new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                builderSingle.show();
                break;
        }
    }
}
