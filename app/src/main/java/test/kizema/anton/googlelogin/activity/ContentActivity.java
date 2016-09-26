package test.kizema.anton.googlelogin.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.kizema.anton.googlelogin.App;
import test.kizema.anton.googlelogin.R;
import test.kizema.anton.googlelogin.adapter.ViewPagerAdapter;
import test.kizema.anton.googlelogin.adapter.ViewPagerAdapter.DataHolder;
import test.kizema.anton.googlelogin.fragment.DemoFragment;
import test.kizema.anton.googlelogin.fragment.GooglePlusProfileFragment;
import test.kizema.anton.googlelogin.helpers.Constants;
import test.kizema.anton.googlelogin.helpers.NotificationFactory;
import test.kizema.anton.googlelogin.helpers.Saver;
import test.kizema.anton.googlelogin.service.AppService;

public class ContentActivity extends AppCompatActivity implements AppService.ServiceConnected {

    @BindView(R.id.tvServiceResult)
    public TextView tvServiceResult;

    @BindView(R.id.pager)
    public ViewPager pager;

    @BindView(R.id.ptTabStrip)
    public PagerTabStrip ptTabStrip;

    @BindView(R.id.btnStopService)
    public Button btnStopService;

    private ViewPagerAdapter viewPagerAdapter;

    private AppService mService;

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            AppService.LocalBinder binder = (AppService.LocalBinder) service;
            mService = binder.getService();
            mService.setCallback(ContentActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            unbindService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!Saver.getInstance().isServiceStopped()) {
            bindService();
        }

        NotificationFactory.cancelNotification();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!Saver.getInstance().isServiceStopped()) {
            unbindService(mConnection);
            unbindService();
        }
    }

    private void unbindService() {
        if (mService != null) {
            mService.setCallback(null);
            mService = null;
        }
    }

    private void bindService(){
        Intent intent = new Intent(this, AppService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void init() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, getAdapterData());
        pager.setAdapter(viewPagerAdapter);

        getRandData();
    }

    private void getRandData() {
        setServiceBtnText(Saver.getInstance().isServiceStopped());
        setRandomNumber(Saver.getInstance().getRand());
    }

    private List<DataHolder> getAdapterData() {
        Intent data = getIntent();
        String name = data.getStringExtra(Constants.NAME);
        String email = data.getStringExtra(Constants.EMAIL);
        String photo = data.getStringExtra(Constants.PHOTO_URL);

        Bundle bndl = new Bundle();
        bndl.putString(Constants.NAME, name);
        bndl.putString(Constants.EMAIL, email);
        bndl.putString(Constants.PHOTO_URL, photo);
        DataHolder googlePlusFragInfo = new DataHolder(GooglePlusProfileFragment.class.getName(),
                bndl, "My G+ profile");

        DataHolder emptyFrag = new DataHolder(DemoFragment.class.getName(), null, "Empty fragment");
        List<DataHolder> dataHolders = new ArrayList<>(2);
        dataHolders.add(googlePlusFragInfo);
        dataHolders.add(emptyFrag);

        return dataHolders;
    }

    @OnClick(R.id.btnStopService)
    public void stopServiceClicked() {

        if (!Saver.getInstance().isServiceStopped()) {
            unbindService(mConnection);
            unbindService();

            App.stopService();

            Saver.getInstance().setServiceStopped(true);
        } else {
            App.startService();

            bindService();

            Saver.getInstance().setServiceStopped(false);
        }

        setServiceBtnText(Saver.getInstance().isServiceStopped());
    }

    private void setServiceBtnText(boolean isServiceStopped){
        if (isServiceStopped){
            btnStopService.setText("Start Service");
        } else {
            btnStopService.setText("Stop Service");
        }
    }

    @Override
    public void setRandomNumber(final int rand) {
        tvServiceResult.setText("Random number : " + rand);
    }
}
