package test.kizema.anton.googlelogin;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.squareup.leakcanary.LeakCanary;

import test.kizema.anton.googlelogin.helpers.NotificationFactory;
import test.kizema.anton.googlelogin.service.AppService;

public class App extends Application {

    private static Context context;

    private static Handler uiHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        uiHandler = new Handler();
        NotificationFactory.init(context);

        LeakCanary.install(this);
    }

    public static void startService(){
        Intent intent = new Intent(context, AppService.class);
        context.startService(intent);
    }

    public static Handler getUiHandler(){
        return uiHandler;
    }
}
