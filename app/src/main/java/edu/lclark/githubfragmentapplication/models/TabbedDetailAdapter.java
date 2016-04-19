package edu.lclark.githubfragmentapplication.models;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import edu.lclark.githubfragmentapplication.R;
import edu.lclark.githubfragmentapplication.fragments.UserFragment;

public class TabbedDetailAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener{

    private final Context mContext;
    private final ViewPager viewPager;
    private final ArrayList<GithubUser> mFollowers=new ArrayList<GithubUser>();
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
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(R.string.follower_list_title, position);
        //return super.getPageTitle(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mActionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        Object tag = tab.getTag();
        for (int i=0; i<mTabs.size(); i++) {
            if (mTabs.get(i) == tag) {
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
}

}
