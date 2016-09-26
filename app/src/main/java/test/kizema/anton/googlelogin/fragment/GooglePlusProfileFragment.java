package test.kizema.anton.googlelogin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.kizema.anton.googlelogin.LoginActivity;
import test.kizema.anton.googlelogin.R;
import test.kizema.anton.googlelogin.helpers.Constants;
import test.kizema.anton.googlelogin.helpers.GooglePlusHelper;

public class GooglePlusProfileFragment extends Fragment {

    @BindView(R.id.imgProfilePic)
    public ImageView imgProfilePic;

    @BindView(R.id.txtName)
    public TextView txtName;

    @BindView(R.id.txtEmail)
    public TextView txtEmail;

    @BindView(R.id.btn_sign_out)
    public Button btnSignOut;

    private GooglePlusHelper googlePlusHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.google_plus_profile_frag, container, false);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init(){

        if (getArguments() != null){
            googlePlusHelper = new GooglePlusHelper(getActivity());

            String personPhotoUrl = getArguments().getString(Constants.PHOTO_URL);
            String name = getArguments().getString(Constants.NAME);
            String email = getArguments().getString(Constants.EMAIL);

            Glide.with(getActivity().getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .error(R.mipmap.ic_launcher)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);

            txtName.setText(name);
            txtEmail.setText(email);
        }
    }

    @OnClick(R.id.btn_sign_out)
    public void signOutClicked(){
        Auth.GoogleSignInApi.signOut(googlePlusHelper.getGoogleApiClient()).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                });
    }

}
