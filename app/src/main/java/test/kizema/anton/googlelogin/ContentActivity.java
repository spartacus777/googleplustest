package test.kizema.anton.googlelogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.kizema.anton.googlelogin.adapter.ViewPagerAdapter;
import test.kizema.anton.googlelogin.adapter.ViewPagerAdapter.DataHolder;
import test.kizema.anton.googlelogin.fragment.DemoFragment;
import test.kizema.anton.googlelogin.fragment.GooglePlusProfileFragment;
import test.kizema.anton.googlelogin.helpers.Constants;

public class ContentActivity extends AppCompatActivity {

    @BindView(R.id.tvServiceResult)
    public TextView tvServiceResult;

    @BindView(R.id.pager)
    public ViewPager pager;

    @BindView(R.id.ptTabStrip)
    public PagerTabStrip ptTabStrip;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        ButterKnife.bind(this);

        init();
    }

    private void init(){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, getAdapterData());
        pager.setAdapter(viewPagerAdapter);
    }

    private List<DataHolder> getAdapterData(){
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
}
