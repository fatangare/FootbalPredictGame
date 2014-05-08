package com.footballpredictgame.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.footballpredictgame.R;
import com.footballpredictgame.network.NetworkRequest;
import com.footballpredictgame.network.NetworkRequestListener;
import com.footballpredictgame.utiity.Constant;
import com.footballpredictgame.utiity.Utility;

public class GroupDialogFragment extends DialogFragment implements
		NetworkRequestListener, OnClickListener {

	public interface GroupDialogFragmentListener {
		void onDismissGroupDialog(String groupId);
	}

	private EditText mTxtGroupName;
	private CheckBox mCBFavorite;
	private String mGroupName;
	private String mIsFav;
	private String mGroupId;
	private Button mBtnSaveGroup;
	private GroupDialogFragmentListener mListener;

	public GroupDialogFragment() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_groupdialog, container);
		mTxtGroupName = (EditText) view.findViewById(R.id.txtName);
		mBtnSaveGroup = (Button) view.findViewById(R.id.btnSaveGroup);
		mCBFavorite = (CheckBox) view.findViewById(R.id.cbFavorite);
		mBtnSaveGroup.setOnClickListener(this);

		Bundle b = getArguments();
		if (b != null) {
			mGroupName = b.getString(Constant.NAME);
			mGroupId = b.getString(Constant.GROUPID);
			mIsFav = b.getString(Constant.FAVORITE);
			getDialog().setTitle(getString(R.string.addgroup));
			mBtnSaveGroup.setText(getString(R.string.addgroup));
		} else {
			mGroupName = "";
			mGroupId = "";
			mIsFav = "0";
			getDialog().setTitle(getString(R.string.editgroup));
			mBtnSaveGroup.setText(getString(R.string.editgroup));
		}

		mTxtGroupName.setText(mGroupName);
		if (mIsFav.compareTo("1") == 0)
			mCBFavorite.setSelected(true);
		else
			mCBFavorite.setSelected(false);

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (GroupDialogFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement GroupDialogFragmentListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onResponse(Object obj) {
		Utility.hideBusyDialog();
		mListener.onDismissGroupDialog(Integer.toString((Integer) obj));
		this.dismiss();
	}

	@Override
	public void onErrorResponse(VolleyError error, Object obj) {
		Utility.hideBusyDialog();
		if (obj == null)
			Utility.showErrorMessage(error, getActivity());
		else {
			Log.v(Constant.TAG, "Group failed");
			int err = (Integer) obj;
			// ToDo: for -1 logout
			if (err == -2) {
				Utility.showAlertDialog(getActivity(),
						R.string.title_alert_error,
						R.string.error_failed_savegroup,
						android.R.drawable.ic_dialog_alert);
			}
			if (err == -3) {
				Utility.showAlertDialog(getActivity(),
						R.string.title_alert_error,
						R.string.error_invalid_groupowner,
						android.R.drawable.ic_dialog_alert);
			}
		}

	}

	@Override
	public void onClick(View v) {
		InputMethodManager imm = (InputMethodManager) (getActivity()
				.getSystemService(getActivity().INPUT_METHOD_SERVICE));
		imm.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);

		String name = mTxtGroupName.getText().toString().trim();
		if (name.isEmpty()) {
			mTxtGroupName.setError(getString(R.string.error_field_required));
			mTxtGroupName.requestFocus();
			return;
		}
		mGroupName = name;
		mIsFav = mCBFavorite.isSelected() ? "1" : "0";
		Utility.showBusyDialog(getActivity(), R.string.progress_group);
		NetworkRequest.insertOrUpdateGroup(mGroupId, mIsFav, mGroupName, this);
	}
}