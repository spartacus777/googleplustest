package test.kizema.anton.googlelogin.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<DataHolder> dataHolders;

    private Context context;

    public static class DataHolder{
        public String fragment;
        public Bundle bundle;
        public String title;

        public DataHolder(String fragment, Bundle bundle, String title){
            this.fragment = fragment;
            this.bundle = bundle;
            this.title = title;
        }
    }

    public ViewPagerAdapter(FragmentManager fragmentManager, Context context,
                            List<DataHolder> fragments) {
        super(fragmentManager);
        this.dataHolders = fragments;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (dataHolders == null){
            return 0;
        }

        return dataHolders.size();
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context, dataHolders.get(position).fragment, dataHolders.get(position).bundle);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dataHolders.get(position).title;
    }

}
