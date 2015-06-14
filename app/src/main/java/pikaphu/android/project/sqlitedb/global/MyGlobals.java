package pikaphu.android.project.sqlitedb.global;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyGlobals{
    Context mContext;

    // constructor
    public MyGlobals(Context context) {
        this.mContext = context;
    }

    public String getSomeStuff() {
        return "test";
    }

    /**
        Permission required in your Manifest file :
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null)
            return false; // There are no active networks.
        else
            return true;
    }
}