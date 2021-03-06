package edu.lclark.githubfragmentapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.ButterKnife;
import edu.lclark.githubfragmentapplication.GithubRecyclerViewAdapter;
import edu.lclark.githubfragmentapplication.NetworkAsyncTask;
import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.models.GithubUser;
import edu.lclark.githubfragmentapplication.models.TabbedDetailAdapter;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements NetworkAsyncTask.GithubListener,
        GithubRecyclerViewAdapter.RowClickListener {

    public static final String ARG_USER = "MainActivityFragment.User";

    ViewPager viewPager;

    /*@Bind(R.id.fragment_main_recyclerview)
    RecyclerView mRecyclerView;*/

    NetworkAsyncTask mAsyncTask;
    GithubRecyclerViewAdapter mAdapter;
    TabbedDetailAdapter Adapter;

    ArrayList<GithubUser> mFollowers;
    private FollowerSelectedListener mListener;

    private String mUserLogin="ntiller";


    public interface FollowerSelectedListener {
        void onFollowerSelected(GithubUser follower);
    }

    public interface TabbedSelectedListener{
        void onTabbedFollowerButtonSelected( GithubUser user);
    }

    public static MainActivityFragment newInstance(GithubUser user) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

       /* mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            GithubUser user = getArguments().getParcelable(ARG_USER);
            if (user != null) {
                mUserLogin = user.getLogin();
            }
        }


        getActivity().setTitle(getString(R.string.follower_list_title, mUserLogin));
        mAdapter = new GithubRecyclerViewAdapter(mFollowers, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mListener = (MainActivity) getActivity();*/

        Button btn = (Button) rootView.findViewById(R.id.fragment_user_tab_button);
        viewPager = (ViewPager) getActivity().findViewById(R.id.activity_main_viewpager);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);


            }
        });



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAsyncTask == null && (mFollowers == null || mFollowers.isEmpty())) {
            mAsyncTask = new NetworkAsyncTask(this);
            mAsyncTask.execute(mUserLogin);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAsyncTask != null && !mAsyncTask.isCancelled()) {
            mAsyncTask.cancel(true);
            mAsyncTask = null;
        }
    }

    @Override
    public void onGithubFollowersRetrieved(@Nullable ArrayList<GithubUser> followers) {
        mFollowers = followers;
        mAdapter.setFollowers(followers);
    }


    @Override
    public void onRowClicked(int position) {
        mListener.onFollowerSelected(mAdapter.getItem(position));
    }

    @Override
    public void onTabbedClicked(int position){
        mListener.onTabbedFollowerButtonSelected(Adapter.getItem(position));

    }
}
