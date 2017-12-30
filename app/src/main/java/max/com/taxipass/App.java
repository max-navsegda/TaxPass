package max.com.taxipass;

import android.support.multidex.MultiDexApplication;

import max.com.taxipass.Utils.Settings;



public class  App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Settings.init(this);
    }
}
