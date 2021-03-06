package com.footballpredictgame.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.android.volley.VolleyError;
import com.footballpredictgame.model.HistoryItem;
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
public class HistoryFragment extends ListFragment implements
		NetworkRequestListener {

	private List<HistoryItem> mHistory;

	public static HistoryFragment newInstance() {
		HistoryFragment fragment = new HistoryFragment();
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public HistoryFragment() {
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
		// Utility.showBusyDialog(getActivity(), R.string.progress_history);
		NetworkRequest.getHistory(this);
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
		// Utility.hideBusyDialog();
		mHistory = (List<HistoryItem>) obj;
		setListAdapter(new ArrayAdapter<HistoryItem>(getActivity(),
				android.R.layout.simple_list_item_1, mHistory));
	}

	@Override
	public void onErrorResponse(VolleyError error, Object obj) {
		// Utility.hideBusyDialog();
		if (obj == null)
			Utility.showErrorMessage(error, getActivity());
		else {
			Log.v(Constant.TAG, "No history record");
		}
	}

}
