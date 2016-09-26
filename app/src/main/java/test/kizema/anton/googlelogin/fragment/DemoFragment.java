package test.kizema.anton.googlelogin.fragment;

import android.support.v4.app.Fragment;

public class DemoFragment extends Fragment {

    public static GooglePlusProfileFragment newInstance() {
        GooglePlusProfileFragment fragmentFirst = new GooglePlusProfileFragment();
        return fragmentFirst;
    }
}
