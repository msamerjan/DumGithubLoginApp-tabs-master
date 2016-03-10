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

    public TabbedDetailAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {

        /*while(*//*some how make this work*//*){

            *//* Here we make an array of gitHub users to call with position*//*

        }*/

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
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(R.string.follower_list_title, position);
    }
}
