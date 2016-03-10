package edu.lclark.githubfragmentapplication.models;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;

public class TabbedDetailAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private ArrayList<GithubUser> mFollowers;
    private TabbedClickListener mListener;

    public interface TabbedClickListener {
        void onTabbedClicked(int position);
    }

    public TabbedDetailAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {

        //return mFollowers == null ? null : mFollowers.get(position);
        Fragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putInt(UserFragment.ARG_USER, position + 1);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getCount() {
        return mFollowers == null ? 0 : mFollowers.size();
        //return mUsers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(R.string.follower_list_title, position);
        //return super.getPageTitle(position);
    }
}
