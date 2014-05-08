package com.footballpredictgame.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.footballpredictgame.R;
import com.footballpredictgame.model.GroupMember;
import com.footballpredictgame.model.Model;
import com.footballpredictgame.network.NetworkRequest;
import com.footballpredictgame.network.NetworkRequestListener;
import com.footballpredictgame.utiity.Constant;
import com.footballpredictgame.utiity.Utility;

public class GroupFragment extends Fragment implements NetworkRequestListener {
	private List<GroupMember> mGroupMembers;
	private ListView mListView;
	private ImageButton mBtnFavorite;
	private TextView mTxtRank;
	private View mRootView;

	private String mGroupName;
	private String mIsFav;
	private String mGroupId;

	public static GroupFragment newInstance(String groupName, String groupId,
			String fav) {
		GroupFragment fragment = new GroupFragment();
		Bundle args = new Bundle();
		args.putString(Constant.GROUPID, groupId);
		args.putString(Constant.NAME, groupName);
		args.putString(Constant.FAVORITE, fav);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public GroupFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// if ((savedInstanceState != null)
		Bundle b = getArguments();
		if (b != null) {
			mGroupName = b.getString(Constant.NAME);
			mGroupId = b.getString(Constant.GROUPID);
			mIsFav = b.getString(Constant.FAVORITE);

		} else {
			mGroupName = "";
			mGroupId = "";
			mIsFav = "0";
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_group, container, false);
		mListView = (ListView) mRootView.findViewById(R.id.listGroupMembers);
		mBtnFavorite = (ImageButton) mRootView.findViewById(R.id.btnFav);
		mTxtRank = (TextView) mRootView.findViewById(R.id.txtRank);
		TextView txtGroupName = (TextView) mRootView
				.findViewById(R.id.txtGroupName);
		txtGroupName.setText(mGroupName);
		if (mIsFav.compareTo("1") == 0)
			mBtnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
		else
			mBtnFavorite.setImageResource(android.R.drawable.btn_star_big_off);

		return mRootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Utility.showBusyDialog(getActivity(), R.string.progress_groupusers);
		NetworkRequest.getGroupMembers("1", this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	/*
	 * @Override public void onListItemClick(ListView l, View v, int position,
	 * long id) { super.onListItemClick(l, v, position, id);
	 * 
	 * if (null != mListener) { // Notify the active callbacks interface (the
	 * activity, if the // fragment is attached to one) that an item has been
	 * selected. mListener
	 * .onFragmentInteraction(DummyContent.ITEMS.get(position).id); } }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public void onResponse(Object obj) {
		Utility.hideBusyDialog();
		mGroupMembers = (List<GroupMember>) obj;
		mListView.setAdapter(new ArrayAdapter<GroupMember>(getActivity(),
				android.R.layout.simple_list_item_1, mGroupMembers));
		mTxtRank.setText(Integer.toString(getUserRank()));
	}

	@Override
	public void onErrorResponse(VolleyError error, Object obj) {
		Utility.hideBusyDialog();
		if (obj == null)
			Utility.showErrorMessage(error, getActivity());
		else {
			Log.v(Constant.TAG, "No group members");
		}
	}

	private int getUserRank() {
		if (mGroupMembers == null || mGroupMembers.isEmpty())
			return -1;

		int cnt = mGroupMembers.size();
		for (int i = 0; i < cnt; i++) {
			if (mGroupMembers.get(i).mUserId
					.compareTo(Model.getCurrentUserId()) == 0) {
				return i + 1;
			}
		}
		return -1;
	}
}
