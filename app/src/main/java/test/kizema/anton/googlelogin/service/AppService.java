package test.kizema.anton.googlelogin.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

import test.kizema.anton.googlelogin.App;
import test.kizema.anton.googlelogin.helpers.NotificationFactory;
import test.kizema.anton.googlelogin.helpers.Saver;

public class AppService extends Service {

    private static final int DELAY_SEC = 1;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private LocalBinder binder;

    private boolean shouldWork = true;

    private volatile ServiceConnected listener;

    public interface ServiceConnected {
        void setRandomNumber(int rand);
    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            startWork();
        }
    }

    public class LocalBinder extends Binder {
        public AppService getService() {
            return AppService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("LOC", "onCreate ");
        binder = new LocalBinder();

        HandlerThread thread = new HandlerThread("ServiceThread",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LOC", "onStartCommand " + startId);

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY;
    }

    private void startWork() {

        while (shouldWork) {
            try {
                Thread.sleep(DELAY_SEC * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!shouldWork) {
                return;
            }

            final int rand = new Random().nextInt(1000000);

            Saver.getInstance().setRand(rand);

            App.getUiHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        listener.setRandomNumber(rand);
                    } else {
                        NotificationFactory.send(rand);
                    }
                }
            });
        }
    }

    public void setCallback(ServiceConnected listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroy() {
        shouldWork = false;
        Log.d("LOC", "onDestroy() ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}



