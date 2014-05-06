package com.footballpredictgame.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.footballpredictgame.fragment.BetFragment;

public class BetFragmentAdapter extends FragmentPagerAdapter {
	protected static final String[] CONTENT = new String[] { "This", "Is" };

	private int mCount = CONTENT.length;

	public BetFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return BetFragment.newInstance(CONTENT[position % CONTENT.length]);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return BetFragmentAdapter.CONTENT[position % CONTENT.length];
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}
}