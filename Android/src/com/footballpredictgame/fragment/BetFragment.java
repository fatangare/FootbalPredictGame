package com.footballpredictgame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footballpredictgame.R;

public final class BetFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Content";

	public static BetFragment newInstance(String content) {
		BetFragment fragment = new BetFragment();
		fragment.mContent = content; // ToDo: remove it later
		return fragment;
	}

	private String mContent = "???";// ToDo: remove it later

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bet, container, false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}
