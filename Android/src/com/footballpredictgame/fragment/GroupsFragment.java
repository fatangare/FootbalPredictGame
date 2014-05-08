package com.footballpredictgame.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.footballpredictgame.R;
import com.footballpredictgame.model.Group;
import com.footballpredictgame.network.NetworkRequest;
import com.footballpredictgame.network.NetworkRequestListener;
import com.footballpredictgame.utiity.Constant;
import com.footballpredictgame.utiity.Utility;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class GroupsFragment extends ListFragment implements
		NetworkRequestListener {

	private List<Group> mGroups;

	public static GroupsFragment newInstance() {
		GroupsFragment fragment = new GroupsFragment();
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public GroupsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		// Utility.showBusyDialog(getActivity(), R.string.progress_groups);
		NetworkRequest.getGroups(this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Group group = mGroups.get(position);
		GroupFragment fragment = GroupFragment.newInstance(group.mName,
				group.mId, "0");
		FragmentTransaction fragmentTransaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragment);
		// it will add screen to home screen when back is pressed
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onResponse(Object obj) {
		// Utility.hideBusyDialog();
		mGroups = (List<Group>) obj;
		if (mGroups != null) {
			setListAdapter(new ArrayAdapter<Group>(getActivity(),
					android.R.layout.simple_list_item_1, mGroups));
		}
	}

	@Override
	public void onErrorResponse(VolleyError error, Object obj) {
		// Utility.hideBusyDialog();
		if (obj == null)
			Utility.showErrorMessage(error, getActivity());
		else {
			Log.v(Constant.TAG, "No groups");
		}
	}

}
