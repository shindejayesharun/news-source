package com.shindejayesharun.newssource.news.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shindejayesharun.newssource.R;
import com.shindejayesharun.newssource.news.App;

public class Utility {
    public static boolean getConnectionStatus(Context context) {
        ConnectivityManager mConnectivityManager;
        NetworkInfo mNetworkInfoMobile, mNetworkInfoWifi;

        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfoMobile = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        mNetworkInfoWifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        try {
            if (mNetworkInfoMobile.isConnected()) {
                App.connectivityStatus = 1;

                return true;
            }
        } catch (Exception exception) {
            // exception.printStackTrace();
        }

        if (mNetworkInfoWifi.isConnected()) {
            App.connectivityStatus = 1;

            return true;
        } else {
            App.connectivityStatus = 0;
            return false;
        }
    }

    public static void alertCheckInternet(Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Offline");
        builder.setMessage("Check Internet Connection");
        builder.setIcon(R.drawable.ic_signal_wifi);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
