package com.footballpredictgame.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.footballpredictgame.R;

/** 
 * Name	  : DrawerListAdapter.java 
 * Version: 1.0 
 * Date   : Apr 11, 2014
 * Copyright (c) 2014 Sandeep Fatangare. All rights reserved. 
 */

/**
 * It is adapter class for drawer list item
 * 
 * @author Sandeep Fatangare India
 */
public class DrawerListAdapter extends ArrayAdapter<String> {

	public static final String TITLE = "TITLE";
	public static final String BET_HEADER = "BET_HEADER";
	public static final String BET_ITEM = "BET_ITEM"; // Bet1, bet2, history,
														// rules
	public static final String GROUP_HEADER = "GROUP_HEADER";
	public static final String GROUP_ITEM = "GROUP_ITEM"; // add, group1-5
	public static final String PROFILE_HEADER = "PROFILE_HEADER";
	public static final String PROFILE_ITEM = "PROFILE_ITEM"; // edit profile,
																// change pwd

	private static class ViewHolder {
		public TextView text;
	}

	private final Activity mContext;
	private final List<String> mDrawerListItems;
	private final List<String> mDrawerListItemKeys;

	/**
	 * DrawerListAdapter constructor
	 * 
	 * @param context
	 * @param textViewResourceId
	 */
	public DrawerListAdapter(Activity context, List<String> drawerListItems,
			List<String> drawerListItemKeys) {
		super(context, R.id.txtDrawerItem);
		mContext = context;
		mDrawerListItems = drawerListItems;
		mDrawerListItemKeys = drawerListItemKeys;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			final String HEADER = "HEADER";
			final String ITEM = "ITEM";
			LayoutInflater inflater = mContext.getLayoutInflater();
			if (mDrawerListItemKeys.get(position).contains(HEADER))
				rowView = inflater.inflate(R.layout.drawer_list_header_item,
						null);
			else
				rowView = inflater.inflate(R.layout.drawer_list_item, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView
					.findViewById(R.id.txtDrawerItem);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.text.setText(mDrawerListItems.get(position));
		if (position == 0) {
			holder.text.setTextColor(Color.rgb(255, 229, 221));// FFE5DD
			holder.text.setBackgroundColor(Color.rgb(171, 0, 0));
		}

		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDrawerListItems.size();
	}

	private View getRowView(int position) {
		LayoutInflater inflater = mContext.getLayoutInflater();
		switch (position) {
		case 0:
		case 1:
		case 4:
			return inflater.inflate(R.layout.drawer_list_header_item, null);
		case 2:
		case 3:
		case 5:
		case 6:
			return inflater.inflate(R.layout.drawer_list_item, null);
		default:
			return null;
		}
	}

	private String getRowText(int position) {
		LayoutInflater inflater = mContext.getLayoutInflater();
		switch (position) {
		case 0:
			return "HELLO SANDEEP";
		case 1:
			return "BET";
		case 2:
			return "MI vs CSK";
		case 3:
			return "DD vs RR";
		case 4:
			return "GROUPS";
		case 5:
			return "Add Group";
		case 6:
			return "Pune";
		default:
			return "";
		}
	}

}
