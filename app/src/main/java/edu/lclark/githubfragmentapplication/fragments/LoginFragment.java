package edu.lclark.githubfragmentapplication.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.UserDetailAsyncTask;
import edu.lclark.githubfragmentapplication.models.GithubUser;


public class LoginFragment extends Fragment {

    public interface LoginListener {
        void onLoginResult(GithubUser user);
    }

    public static final String GITHUB_LOGO_URL = "https://assets-cdn.github.com/images/modules/logos_page/GitHub-Mark.png";

    @Bind(R.id.login_main_image)
    ImageView mImageView;

    @Bind(R.id.login_main_username_editText)
    EditText mEditText;

    @Bind(R.id.fragment_main_login_button)
    Button mButton;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    UserDetailAsyncTask mGithubUserAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_login_main, container, false);
        ButterKnife.bind(this, rootView);

        getActivity().setTitle(getActivity().getString(R.string.sign_in));
        Picasso.with(getActivity()).load(GITHUB_LOGO_URL).fit().centerInside().into(mImageView);

        return rootView;
    }

    @OnClick(R.id.fragment_main_login_button)
    void searchForUser() {
        if (!hasInternetConnection()) {
            Toast toast = Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        mButton.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);

        mGithubUserAsyncTask = new UserDetailAsyncTask((LoginListener) getActivity());
        mGithubUserAsyncTask.execute(mEditText.getText().toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGithubUserAsyncTask != null) {
            mGithubUserAsyncTask.cancel(false);
        }
    }

    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}